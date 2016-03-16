package com.computer.team8.DSTR.projectui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.computer.team8.DSTR.R;

/**
 * Created by Catherine on 3/16/2016.
 */
public class MessageBox {
    private Dialog dialog;

    public MessageBox(Context context, String message)
    {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogbox);

        TextView text = (TextView) dialog.findViewById(R.id.dialogMessage);
        text.setText(message);

        ImageButton okButton = (ImageButton) dialog.findViewById(R.id.dialogOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void show()
    {
        dialog.show();
    }
}
