package com.example.notewise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddNewFileDialog extends DialogFragment {

    public interface NoticeDialogListener {
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.add_new, null);
        EditText editText = layout.findViewById(R.id.new_name);
        editText.setHint(R.string.add_file_hint);

        builder.setMessage(R.string.add_new_file)
                .setView(layout)
                .setPositiveButton(R.string.create_note_file, null)
                .setNegativeButton(R.string.create_todo_file, null)
                .setNeutralButton(R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                String fileName = editText.getText().toString();
                if (nameOK(fileName, layout)) {
                    listener.createNoteFile(fileName);
                    dialog.dismiss();
                }
            });

            button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            button.setOnClickListener(view -> {
                String fileName = editText.getText().toString();
                if (nameOK(fileName, layout)) {
                    listener.createTodoFile(fileName);
                    dialog.dismiss();
                }
            });
        });

        return dialog;
    }



    private boolean nameOK(String name, LinearLayout layout) {
        if (name.equals("")) {
            EditText editText = layout.findViewById(R.id.new_name);
            TextView errorText = layout.findViewById(R.id.name_error_text);
            editText.setBackgroundResource(R.drawable.name_error_border);
            errorText.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }
}
