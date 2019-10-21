package com.example.notewise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FileHandling.File;
import FileHandling.FileManager;
import FileHandling.Folder;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements AddNewFileDialog.NoticeDialogListener, AddFolderDialog.NoticeDialogListener{

    private ExpandingList expandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDB();

//        FileManager fmInstance = FileManager.getInstance();
//        fmInstance.createFolder("My folder");
//
//        updateExpandingList();
    }

    private void initializeDB() {
        Paper.init(this);
        expandingList = findViewById(R.id.expanding_list_main);
        createExpandingList();
    }

    private void createExpandingList() {
        FileManager fmInstance = FileManager.getInstance();
        HashMap<String, Folder> folders = fmInstance.getAllFolders();

        for (Map.Entry<String, Folder> entry : folders.entrySet()) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
            String folderName = entry.getValue().getName();
            item.setOnClickListener(view -> {
                Folder folder = fmInstance.getFolder(folderName, true);
                fmInstance.updateContext(folder.getID(), "");
            });

            ((TextView) item.findViewById(R.id.parent_text)).setText(entry.getValue().getName());

            item.findViewById(R.id.folder_menu_btn).setOnClickListener(view -> {
                PopupMenu folderMenuPopup = new PopupMenu(this, view);
                MenuInflater inflater = folderMenuPopup.getMenuInflater();
                inflater.inflate(R.menu.folder_menu, folderMenuPopup.getMenu());
                folderMenuPopup.setOnMenuItemClickListener(item1 -> {
                    fmInstance.updateContext(entry.getKey(), "");
                    switch (item1.getItemId()) {
                        case R.id.menu_add_file:
                            DialogFragment dialog = new AddNewFileDialog();
                            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                            return true;
                        case R.id.menu_rename_folder:

                            // rename dialog

                            return true;
                        case R.id.menu_delete_folder:

                            // delete folder

                            return true;
                        default:
                            return false;
                    }
                });
                folderMenuPopup.show();
            });

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
            item.setIndicatorColorRes(R.color.colorFolder);
            item.setIndicatorIconRes(R.drawable.folder_close);
            }
        }
    }

    private void updateExpandingList() {
        expandingList.removeAllViews();
        createExpandingList();
    }


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

    @Override
    public void createFolder(String folderName) {
        FileManager.getInstance().createFolder(folderName);
        updateExpandingList();
        RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
        floatingAddExpand.setVisibility(View.GONE);
    }

    public void onAddFileBtnClick(View view) {
//        String folderName = ((TextView)findViewById(R.id.parent_text)).getText().toString();
//        FileManager fmInstance = FileManager.getInstance();
//        Folder folder = fmInstance.getFolder(folderName, true);
//        fmInstance.updateContext(folder.getID(), "");

//        DialogFragment dialog = new AddNewFileDialog();
//        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    public void onFloatingAddBtnClick(View view) {
        ViewGroup transitionContainer = findViewById(R.id.floating_layout_homescene);
        TransitionManager.beginDelayedTransition(transitionContainer);
        RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
        floatingAddExpand.setVisibility(floatingAddExpand.getVisibility() == View.VISIBLE? View.GONE : View.VISIBLE);
    }

    public void onAddFolderBtnClick(View view) {
        DialogFragment dialog = new AddFolderDialog();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    public void openFolderMenu(View view) {
        PopupMenu folderMenuPopup = new PopupMenu(this, view);
        MenuInflater inflater = folderMenuPopup.getMenuInflater();
        inflater.inflate(R.menu.folder_menu, folderMenuPopup.getMenu());
        folderMenuPopup.setOnMenuItemClickListener(item1 -> {
            switch (item1.getItemId()) {
                case R.id.menu_add_file:
                    String folderName = ((TextView)findViewById(R.id.parent_text)).getText().toString();
                    FileManager fmInstance = FileManager.getInstance();
                    Folder folder = fmInstance.getFolder(folderName, true);
                    fmInstance.updateContext(folder.getID(), "");

                    DialogFragment dialog = new AddNewFileDialog();
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                    return true;
                case R.id.menu_rename_folder:
                    // do your code
                    return true;
                case R.id.menu_delete_folder:
                    // do your code
                    return true;
                default:
                    return false;
            }
        });
        folderMenuPopup.show();
    }
}











