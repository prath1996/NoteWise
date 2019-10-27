package com.example.notewise;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.io.IOException;

public class SimpleDialog extends DialogFragment {

    public interface DialogBridge {
        void onPositiveClick() throws Exception;
        void onNegativeClick();
    }

    private String dialogMessage;
    private String positiveBtnText;
    private String defaulFieldEmptyError = "Field can't be empty";

    private SimpleDialog.DialogBridge bridge;

    public SimpleDialog(String dialogMessage,
                        String positiveBtnText,
                        SimpleDialog.DialogBridge bridge) {
        this.dialogMessage = dialogMessage;
        this.positiveBtnText = positiveBtnText;
        this.bridge = bridge;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(dialogMessage)
                .setPositiveButton(positiveBtnText, null)
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {

            Button posBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            posBtn.setOnClickListener(view -> {
                try {
                    bridge.onPositiveClick();
                    dialog.dismiss();
                } catch (Exception e) {
                    String errMsg = e.getMessage() != null ? e.toString():defaulFieldEmptyError;
                    Log.e(getString(R.string.TAG_err), errMsg);
                }

            });

            Button negBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negBtn.setOnClickListener(view -> {
                bridge.onNegativeClick();
                dialog.dismiss();
            });
        });
        return dialog;
    }
}
