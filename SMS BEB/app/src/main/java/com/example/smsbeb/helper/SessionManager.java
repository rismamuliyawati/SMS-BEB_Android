package com.example.smsbeb.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smsbeb.LoginActivity;
import com.example.smsbeb.MainActivity;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String NIS = "NIS";
    public static final String NAMA = "NAMA";
    public static final String ID_KELAS = "ID_KELAS";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String nis, String nama, String id_kelas) {
        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(NIS, nis);
        editor.putString(NAMA, nama);
        editor.putString(ID_KELAS, id_kelas);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent login = new Intent(context, LoginActivity.class);
        context.startActivity(login);
        ((MainActivity)context).finish();
    }

    public String getNis() {
        return sharedPreferences.getString(NIS, null);
    }

    public String getNama() {
        return sharedPreferences.getString(NAMA, null);
    }

    public String getIdKelas() {
        return sharedPreferences.getString(ID_KELAS, null);
    }
}