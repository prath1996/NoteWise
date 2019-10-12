package com.example.notewise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import FileHandling.FileManager;

public class AddNewFileDialog extends DialogFragment {

    public interface NoticeDialogListener {
        void beforeTextChanged();
        void afterTextChanged();
        void onTextChanged();
        void createNoteFile(String fileName);
        void createTodoFile(String fileName);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            Log.e("notewise", e.getMessage());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.add_new_file, null);

        EditText editText = layout.findViewById(R.id.new_file_name);

        builder.setMessage(R.string.add_new_file)
                .setView(layout)
                .setPositiveButton(R.string.create_note_file, (dialog, id) -> {
                    String fileName = editText.getText().toString();
                    listener.createNoteFile(fileName);
                })
                .setNegativeButton(R.string.create_todo_file, (dialog, id) -> {
                    String fileName = editText.getText().toString();
                    listener.createTodoFile(fileName);
                })
                .setNeutralButton(R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                });
        return builder.create();
    }
}
