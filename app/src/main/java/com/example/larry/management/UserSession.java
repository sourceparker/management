package com.example.larry.management;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {


    SharedPreferences preferences;
    Context mContext;

    SharedPreferences.Editor editor;

    public UserSession(Context context){


        mContext=context;
        preferences=context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        editor=preferences.edit();


    }

    public void setLoggedIn(boolean logged){

        editor.putBoolean("loggedinmode",logged);
        editor.apply();
    }

    public boolean loggedIn(){

        return preferences.getBoolean("loggedinmode",false);
    }

    public void removeUser(){
        preferences.edit().clear().apply();
    }
}
