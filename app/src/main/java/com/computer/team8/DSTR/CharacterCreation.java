package com.computer.team8.DSTR;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.computer.team8.DSTR.projectui.*;

public class CharacterCreation extends AppCompatActivity {

    BackgroundMusic bgm;
    Context activityContext;

    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        activityContext = this;

        bgm = new BackgroundMusic(activityContext);
        bgm.addFile(R.raw.customization, "custom");
        bgm.play("custom");

        //Load detail from memory.
        loadData();

    }

    private void loadData()
    {
        txtName = (TextView)findViewById(R.id.txtName);
        String n = Preferences.getName(activityContext);

        txtName.setText(n);
    }

    public void toMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeName(View view)
    {
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.name_dialog);

        //Set cancel button to dismiss.
        ImageButton cancelButton = (ImageButton) dialog.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Set image button to perform rename.
        ImageButton okButton = (ImageButton) dialog.findViewById(R.id.buttonOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameFinal = (EditText) (dialog.findViewById(R.id.inputName));
                String characterName = nameFinal.getText().toString();
                txtName.setText(characterName);
                Preferences.setName(activityContext, characterName);

                dialog.dismiss();
            }
        });

        dialog.show();
       // dialog.getWindow().setLayout(400, 150); //Uncomment and adjust window size if you cannot see the window.

    }

    public void changeEyes(View view)
    {
        Log.i("MY_MESSAGE", "Eyes");
    }

    public void changeBody(View view)
    {
        Log.i("MY_MESSAGE", "Body");
    }

    public void changeHorns(View view)
    {
        Log.i("MY_MESSAGE", "Horns");
    }

    public void selectWhite(View view)
    {
        Log.i("MY_MESSAGE", "White");
    }

    public void selectBlue(View view)
    {
        Log.i("MY_MESSAGE", "Blue");
    }

    public void selectYellow(View view)
    {
        Log.i("MY_MESSAGE", "Yellow");
    }

    public void selectRed(View view)
    {
        Log.i("MY_MESSAGE", "Red");
    }

    public void selectAqua(View view)
    {
        Log.i("MY_MESSAGE", "Aqua");
    }

    public void selectGreen(View view)
    {
        Log.i("MY_MESSAGE", "Green");
    }

    @Override
    public void onPause()
    {
        bgm.stop();
        super.onPause();
    }


    @Override
    public void onResume()
    {
        bgm.play("custom");
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        bgm.destroy();
        super.onDestroy();
    }

}
