package com.computer.team8.DSTR;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.computer.team8.DSTR.projectui.*;

public class CharacterCreation extends Activity {

    BackgroundMusic bgm;
    Context activityContext;

    TextView txtName;
    ImageView image;

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


        image = (ImageView) findViewById(R.id.square);
        String str = Preferences.getColor(activityContext);
        Log.i("CURRENT COLOR:", str);

        if(str == "pure red"){
            image.setImageResource(R.drawable.red_square);
        }
        else if(str == "white"){
            image.setImageResource(R.drawable.white_square);
        }
        else if(str == "green"){
            image.setImageResource(R.drawable.green_square);
        }
        else if(str == "yellow"){
            image.setImageResource(R.drawable.yellow_square);
        }
        else if(str == "aqua"){
            image.setImageResource(R.drawable.aqua_square);
        }
        else if(str == "blue"){
            image.setImageResource(R.drawable.blue_square);
        }
        else{
            image.setImageResource(R.drawable.red_square);
        }

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
        //Create a dialog box.
        Log.i("MY_MESSAGE", "White");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.white_square);
                String characterColor = "white";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void selectBlue(View view)
    {
        Log.i("MY_MESSAGE", "Blue");
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.blue_square);
                String characterColor = "blue";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectYellow(View view)
    {
        Log.i("MY_MESSAGE", "Yellow");
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.yellow_square);
                String characterColor = "yellow";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectRed(View view)
    {
        Log.i("MY_MESSAGE", "Red");
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.red_square);
                String characterColor = "pure red";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectAqua(View view)
    {
        Log.i("MY_MESSAGE", "Aqua");
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.aqua_square);
                String characterColor = "aqua";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectGreen(View view)
    {
        Log.i("MY_MESSAGE", "Green");
        //Create a dialog box.
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_dialog);

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
                ImageView image = (ImageView) findViewById(R.id.square);
                image.setImageResource(R.drawable.green_square);
                String characterColor = "green";
                Preferences.setColor(activityContext, characterColor);
                dialog.dismiss();
            }
        });

        dialog.show();
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
