package com.example.notewise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.noodle.Noodle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<String> listGroup;
    HashMap<String, List<String>> listItem;

    private ExpandingList expandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateDBInstance();

        FileManager fmInstance = FileManager.getInstance();
//        fmInstance.createFolder("My folder");

        updateExpandingList();
    }

    private void instantiateDBInstance() {
        Noodle noodle = Noodle.with(this).addType(Folder.class).build();
        DBHandler.init(noodle.collectionOf(Folder.class));
        expandingList = findViewById(R.id.expanding_list_main);
    }

    private void updateExpandingList() {
        FileManager fmInstance = FileManager.getInstance();
        HashMap<Long, Folder> folders = fmInstance.getAllFolders();

        for (Map.Entry<Long, Folder> entry : folders.entrySet()) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
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

    public void onFolderNameClick(View view) {
        String folderName = ((TextView)view.findViewById(R.id.parent_text)).getText().toString();
        FileManager fmInstance = FileManager.getInstance();

        Log.e("notewise", "folderName: " + folderName);

        // update contextInfo
        Folder folder = fmInstance.getFolder(folderName);
        fmInstance.updateContext(folder.getID());
    }
}











