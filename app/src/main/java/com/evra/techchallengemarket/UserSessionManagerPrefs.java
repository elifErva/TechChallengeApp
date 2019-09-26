package com.evra.techchallengemarket;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManagerPrefs {

    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "AndroidExamplePref";
    private static final String IS_USER_LOGIN = "login";

    public UserSessionManagerPrefs(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, true);
    }
    public void setSession(){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.commit();
    }
    public void setForgetSession(){
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.commit();
    }
}
