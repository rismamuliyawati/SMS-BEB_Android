package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAMA = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NO_HP = "NO_HP";
    public static final String NAMA = "NAMA";
    public static final String EMAIL = "EMAIL";
    public static final String FOTO = "FOTO";
    public static final String NIS = "NIS";
    public static final String TEMPAT_LAHIR = "TEMPAT_LAHIR";
    public static final String TANGGAL_LAHIR = "TANGGAL_LAHIR";
    public static final String JENIS_KELAMIN = "JENIS_KELAMIN";
    public static final String ID_KELAS = "ID_KELAS";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAMA, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession( String nama, String email, String foto, String no_hp, String nis, String tempat_lahir, String tanggal_lahir, String jenis_kelamin, String id_kelas){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAMA, nama);
        editor.putString(EMAIL, email);
        editor.putString(FOTO, foto);
        editor.putString(NO_HP, no_hp);
        editor.putString(NIS, nis);
        editor.putString(TEMPAT_LAHIR, tempat_lahir);
        editor.putString(TANGGAL_LAHIR, tanggal_lahir);
        editor.putString(JENIS_KELAMIN, jenis_kelamin);
        editor.putString(ID_KELAS, id_kelas);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (!this.isLogin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((Main3Activity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(FOTO, sharedPreferences.getString(FOTO, null));
        user.put(NO_HP, sharedPreferences.getString(NO_HP, null));
        user.put(NIS, sharedPreferences.getString(NIS, null));
        user.put(TEMPAT_LAHIR, sharedPreferences.getString(TEMPAT_LAHIR, null));
        user.put(TANGGAL_LAHIR, sharedPreferences.getString(TANGGAL_LAHIR, null));
        user.put(JENIS_KELAMIN, sharedPreferences.getString(JENIS_KELAMIN, null));
        user.put(ID_KELAS, sharedPreferences.getString(ID_KELAS, null));

        return user;
    }
//
//    public String getid() {
//        return sharedPreferences.getString(ID, null);
//    }
}
