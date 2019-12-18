package com.assignment.androidproject.base_model;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.androidproject.R;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressDialogManager {
    private static ProgressDialog progressDialog;

    private ProgressDialogManager() {

    }

    public static ProgressDialog getInstance(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        return progressDialog;
    }

    public static void showProgressDialog(Context context) {
        try {
            if (!((AppCompatActivity) context).isFinishing()) {

                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(context, null, null);
                } else {
                    if (!progressDialog.isShowing()) {
                        progressDialog = ProgressDialog.show(context, null, null);
                    }
                }
                progressDialog.setContentView(R.layout.custom_loader_view);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                if (progressDialog.getWindow() == null) return;
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        progressDialog.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 15000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog(Context context) {
        try {
            if (progressDialog != null && progressDialog.isShowing() && !((AppCompatActivity) context).isFinishing()) {
                progressDialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
