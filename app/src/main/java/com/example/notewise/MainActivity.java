package com.example.notewise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity {

    private ExpandingList expandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDB();
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
                            DialogFragment dialog1 = new TextDialog(getString(R.string.add_new_file),
                                    getString(R.string.add_file_hint),
                                    getString(R.string.create_note_file),
                                    getString(R.string.create_todo_file),
                                    new TextDialog.DialogBridge() {
                                        @Override
                                        public void onPositiveClick(String editText) throws Exception{
                                            try {
                                                FileManager.getInstance().addNoteFile(editText);
                                                updateExpandingList();
                                                RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
                                                floatingAddExpand.setVisibility(View.GONE);
                                            } catch (Exception e) {
                                                throw new Exception("Create Note file: ".concat(e.toString()));
                                            }
                                        }

                                        @Override
                                        public void onNeutralClick(String editText) throws Exception{
                                            try {
                                                FileManager.getInstance().addTodoFile(editText);
                                                updateExpandingList();
                                                RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
                                                floatingAddExpand.setVisibility(View.GONE);
                                            } catch (Exception e) {
                                                throw new Exception("Create ToDo file: ".concat(e.toString()));
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {}
                                    });
                            dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment");
                            return true;
                        case R.id.menu_rename_folder:
                            DialogFragment dialog2 = new TextDialog(getString(R.string.rename_folder),
                                    getString(R.string.add_folder_hint),
                                    getString(R.string.rename_folder_btn),
                                    null,
                                    new TextDialog.DialogBridge() {
                                        @Override
                                        public void onPositiveClick(String editText) throws Exception{
                                            try {
                                                FileManager.getInstance().renameFolder(editText);
                                                updateExpandingList();
                                                RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
                                                floatingAddExpand.setVisibility(View.GONE);
                                            } catch (Exception e) {
                                                throw new Exception("Rename folder: ".concat(e.toString()));
                                            }
                                        }

                                        @Override
                                        public void onNeutralClick(String s) {}

                                        @Override
                                        public void onNegativeClick() {}
                                    });
                            dialog2.show(getSupportFragmentManager(), "NoticeDialogFragment");
                            return true;
                        case R.id.menu_delete_folder:
                            DialogFragment dialog3 = new SimpleDialog(getString(R.string.delete_folder),
                                    getString(R.string.yes),
                                    new SimpleDialog.DialogBridge() {
                                        @Override
                                        public void onPositiveClick() throws Exception{
                                            try {
                                                FileManager.getInstance().deleteFolder();
                                                updateExpandingList();
                                            } catch (Exception e) {
                                                throw new Exception("Delete Folder: ".concat(e.toString()));
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {}
                                    });
                            dialog3.show(getSupportFragmentManager(), "NoticeDialogFragment");
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

    public void floatingAddBtnClick(View view) {
        ViewGroup transitionContainer = findViewById(R.id.floating_layout_homescene);
        TransitionManager.beginDelayedTransition(transitionContainer);
        RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
        floatingAddExpand.setVisibility(floatingAddExpand.getVisibility() == View.VISIBLE? View.GONE : View.VISIBLE);
    }

    public void addFolderBtnClick(View view) {
        DialogFragment dialog = new TextDialog(getString(R.string.add_new_folder),
                getString(R.string.add_folder_hint),
                getString(R.string.create_folder),
                null,
                new TextDialog.DialogBridge() {
                    @Override
                    public void onPositiveClick(String editText) throws Exception{
                        try {
                            FileManager.getInstance().createFolder(editText);
                            updateExpandingList();
                            RelativeLayout floatingAddExpand = findViewById(R.id.add_floating_expand_layout);
                            floatingAddExpand.setVisibility(View.GONE);
                        } catch (Exception e) {
                            throw new Exception("Create Folder: ".concat(e.toString()));
                        }
                    }

                    @Override
                    public void onNeutralClick(String s) {}

                    @Override
                    public void onNegativeClick() {}
                });
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }
}











