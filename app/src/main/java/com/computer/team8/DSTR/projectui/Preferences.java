package com.computer.team8.DSTR.projectui;

import android.content.Context;
import android.content.SharedPreferences;

//Wrapper class for accessing shared preferences.
//Referenced: http://stackoverflow.com/questions/23351904/getting-cannot-resolve-method-error-when-trying-to-implement-getsharedpreferen

public class Preferences {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey";
    private SharedPreferences sharedpreferences;

    public Preferences()
    {
        //Nothing here
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getName(Context context)
    {
        return getPreferences(context).getString(name, "Anonymous");
    }

    public static void setName(Context context, String characterName)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(name, characterName);
        editor.commit();
    }

    //Also get and set colors here.

}
