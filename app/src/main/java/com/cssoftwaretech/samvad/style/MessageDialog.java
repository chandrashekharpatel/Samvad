package com.cssoftwaretech.samvad.style;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cssoftwaretech.samvad.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MessageDialog {
    static AlertDialog.Builder dialog;
    static TextView tvHeader, tvMessage;
    static LayoutInflater inflater;
    static View layout;
    static ImageView imgIcon;
    static Button btnOK, btnCancel;
    static AlertDialog Dial;

    public static void messSure(final Context context, String title, String message) {
        try {
            initialization(context);
            tvHeader.setText(title);
            tvMessage.setText(message);
            btnCancel.setText("NO");
            btnOK.setText("YES");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dial.dismiss();
                }
            });
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dial.dismiss();
                }
            });
            Dial.show();
        } catch (Exception e) {
            Toast.makeText(context, "Err In MSG", Toast.LENGTH_LONG).show();
        }
    }

    private static void initialization(Context context) {
        if (Dial != null) {
            Dial.dismiss();
        }
        dialog = new AlertDialog.Builder(context);
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.message_dialog, null);
        tvHeader = (TextView) layout.findViewById(R.id.tvMD_header);
        tvMessage = (TextView) layout.findViewById(R.id.tvMD_message);
        imgIcon = (ImageView) layout.findViewById(R.id.imgMD_icon);
        btnOK = (Button) layout.findViewById(R.id.btnMD_OK);
        btnCancel = (Button) layout.findViewById(R.id.btnMD_cancel);
        Dial = dialog.create();
        Dial.setCancelable(false);
        Dial.setView(layout);
    }


    public static void exceptionAlert(Context context, String message, Exception e) {
        e.printStackTrace();
        print("Exception", e + "");
        messAlert(context, "Exception", message + "-" + e, 4);
    }
    public static void toastMess(Context context, String message, boolean longTime) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_alert, null);
        TextView tvMessage = (TextView) layout.findViewById(R.id.tvTA_message);
        tvMessage.setText(message);
        Toast s = new Toast(context);
        s.setView(layout);
        s.show();
        print(context.getClass().toString(), message);
    }

    public static void messAlert(final Context context, String title, String message, int iconId) {
        try {
            initialization(context);
            tvHeader.setText(title);
            tvMessage.setText(message);
            if (iconId == 0) {
            } else if (iconId == 1) {
                imgIcon.setImageResource(R.drawable.ic_info_message);
            } else if (iconId == 2) {
                imgIcon.setImageResource(R.drawable.ic_error_message);
            } else if (iconId == 3) {
                imgIcon.setImageResource(R.drawable.ic_warning_message);
            } else if (iconId == 4) {
                imgIcon.setImageResource(R.drawable.ic_exception_message);
            } else {
                imgIcon.setImageResource(iconId);
            }
            btnCancel.setVisibility(View.INVISIBLE);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dial.dismiss();
                }
            });
            Dial.show();
        } catch (Exception e) {
            Log.e("Exception", "MessNotice");
        }
    }


    public static void print(int message) {
        logPrint("PRINT-Int ", message + "");
    }

    public static void print(String message) {
        logPrint("PRINT-S ", message);
    }

    public static void error(String message) {
        logPrint("Error ", message);
    }

    public static void print(String title, String message) {
        logPrint(title, message);
    }
    private static void logPrint(String title, String message) {
        Log.e(title, message);
    }

}
