/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.ui.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/16/2017.
 */

public class ConfirmationDialog {

    public final static String DIALOG_TITLE = "Confirm action";
    public final static String DIALOG_MESSAGE = "Do you want to clear search results?";

    private final Context mContext;
    private final OnConfirmActionListener mConfirmActionListener;

    public ConfirmationDialog(Context context, OnConfirmActionListener actionListener) {
        mContext = context;
        mConfirmActionListener = actionListener;
    }

    public void showConfirmationDialog() {
        show(DIALOG_TITLE, DIALOG_MESSAGE, android.R.drawable.ic_dialog_alert);
    }

    public void show(String title, String message, int icon) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mConfirmActionListener != null) {
                            mConfirmActionListener.onConfirmAction(true);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mConfirmActionListener != null) {
                            mConfirmActionListener.onConfirmAction(false);
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public interface OnConfirmActionListener {

        void onConfirmAction(boolean confirmed);
    }
}
