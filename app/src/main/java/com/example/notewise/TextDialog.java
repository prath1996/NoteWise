package com.example.notewise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TextDialog extends DialogFragment {

    public interface DialogBridge {
        void onPositiveClick(String editText) throws Exception;
        void onNeutralClick(String editText) throws Exception;
        void onNegativeClick();
    }

    private String dialogMessage;
    private String editTextHint;
    private String positiveBtnText;
    private String neutralBtnText;
    private String defaulFieldEmptyError = "Field can't be empty";

    private DialogBridge bridge;

    public TextDialog(String dialogMessage,
                      String editTextHint,
                      String positiveBtnText,
                      String neutralBtnText,
                      DialogBridge bridge) {
        this.dialogMessage = dialogMessage;
        this.editTextHint = editTextHint;
        this.positiveBtnText = positiveBtnText;
        this.neutralBtnText = neutralBtnText;
        this.bridge = bridge;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.add_new, null);
        EditText editText = layout.findViewById(R.id.new_name);
        editText.setHint(editTextHint);

        TextView errorText = layout.findViewById(R.id.name_error_text);

        builder.setMessage(dialogMessage)
                .setView(layout)
                .setPositiveButton(positiveBtnText, null)
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                });

        if (neutralBtnText != null && !neutralBtnText.equals("")) {
            builder.setNeutralButton(neutralBtnText, null);
        }

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {

            Button posBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            posBtn.setOnClickListener(view -> {
                String text = editText.getText().toString();
                if (text.equals("")) {
                    errorText.setText(getString(R.string.empty_field_err));
                    editText.setBackgroundResource(R.drawable.name_error_border);
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }
                try {
                    bridge.onPositiveClick(text);
                    dialog.dismiss();
                } catch (Exception e) {
                    errorText.setText(e.toString());
                    editText.setBackgroundResource(R.drawable.name_error_border);
                    errorText.setVisibility(View.VISIBLE);
                }
            });

            if (neutralBtnText != null && !neutralBtnText.equals("")) {
                Button neutralBtn = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                neutralBtn.setOnClickListener(view -> {
                    String text = editText.getText().toString();
                    if (text.equals("")) {
                        errorText.setText(getString(R.string.empty_field_err));
                        editText.setBackgroundResource(R.drawable.name_error_border);
                        errorText.setVisibility(View.VISIBLE);
                        return;
                    }
                    try {
                        bridge.onNeutralClick(text);
                        dialog.dismiss();
                    } catch (Exception e) {
                        errorText.setText(e.toString());
                        editText.setBackgroundResource(R.drawable.name_error_border);
                        errorText.setVisibility(View.VISIBLE);
                    }
                });
            }

            Button negBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negBtn.setOnClickListener(view -> {
                    bridge.onNegativeClick();
                    dialog.dismiss();
            });
        });
        return dialog;
    }
}
