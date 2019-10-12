package com.example.notewise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.noodle.Noodle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FileHandling.DBHandler;
import FileHandling.File;
import FileHandling.FileManager;
import FileHandling.Folder;

public class MainActivity extends AppCompatActivity implements AddNewFileDialog.NoticeDialogListener {

    List<String> listGroup;
    HashMap<String, List<String>> listItem;

    private ExpandingList expandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateDBInstance();

//        FileManager fmInstance = FileManager.getInstance();
//        fmInstance.createFolder("My folder");
//
//        updateExpandingList();
    }

    private void instantiateDBInstance() {
        Noodle noodle = Noodle.with(this).addType(Folder.class).build();
        DBHandler.init(noodle.collectionOf(Folder.class));
        expandingList = findViewById(R.id.expanding_list_main);
        createExpandingList();
    }

    private void createExpandingList() {
        FileManager fmInstance = FileManager.getInstance();
        HashMap<Long, Folder> folders = fmInstance.getAllFolders();

        for (Map.Entry<Long, Folder> entry : folders.entrySet()) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
            item.setOnClickListener(view -> {
                String folderName = ((TextView)view.findViewById(R.id.parent_text)).getText().toString();
                Folder folder = fmInstance.getFolder(folderName);
                fmInstance.updateContext(folder.getID());
            });

            ((TextView) item.findViewById(R.id.parent_text)).setText(entry.getValue().getName());

            List<File> fileList = fmInstance.getAllFiles(entry.getKey());

            if (fileList != null) {
                int size = fileList.size();
                item.createSubItems(size);

                int count = 0;
                for (File file: fileList) {
                    View fileView = item.getSubItemView(count);
                    ((TextView) fileView.findViewById(R.id.child_text)).setText(file.getName());
                    count++;
                }
//            item.setIndicatorColorRes(R.color.blue);
//            item.setIndicatorIconRes(R.drawable.ic_icon);
            }
        }
    }

    private void updateExpandingList() {
        expandingList.removeAllViews();
        createExpandingList();
    }

    public void showNoticeDialog() {
        DialogFragment dialog = new AddNewFileDialog();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }


    @Override
    public void beforeTextChanged() {}

    @Override
    public void afterTextChanged() {}

    @Override
    public void onTextChanged() {}

    @Override
    public void createNoteFile(String fileName) {
        FileManager.getInstance().addNoteFile(fileName);
        updateExpandingList();
    }

    @Override
    public void createTodoFile(String fileName) {
        FileManager.getInstance().addTodoFile(fileName);
        updateExpandingList();
    }

    public void onAddFileBtnClick(View v) {
        String folderName = ((TextView)findViewById(R.id.parent_text)).getText().toString();
        FileManager fmInstance = FileManager.getInstance();
        Folder folder = fmInstance.getFolder(folderName);
        fmInstance.updateContext(folder.getID());
        showNoticeDialog();
    }
}











