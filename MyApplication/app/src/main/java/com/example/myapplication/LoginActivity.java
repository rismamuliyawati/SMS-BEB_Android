package com.example.myapplication;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.api.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    private EditText Username, Password;
    private Button btn_login;
    private ProgressBar loading;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        loading = findViewById(R.id.loading);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for splash

        Username = (EditText) findViewById(R.id.txt_username);
        Password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameKey = Username.getText().toString().trim();
                String passwordKey = Password.getText().toString().trim();

                if (!usernameKey.isEmpty() || !passwordKey.isEmpty()) {
                    Login(usernameKey, passwordKey);
                } else {
                    Username.setError("Please insert Username");
                    Password.setError("Please insert Password");
                }
            }

        });

    }

    private void Login(final String Username, final String Password) {

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                    JSONObject data = jsonObject.getJSONObject("data");

                                String level = jsonObject.getString("level");
                                if (level.equals("siswa")){

                                    String nama = data.getString("nama").trim();
                                    String email = data.getString("email").trim();
                                    String foto = "http://smsbeb.mif-project.com/foto/siswa/" + data.getString("foto").trim();
                                    String no_hp = data.getString("no_hp").trim();
                                    String nis = data.getString("nis").trim();
                                    String tempat_lahir = data.getString("tempat_lahir").trim();
                                    String tanggal_lahir = data.getString("tanggal_lahir").trim();
                                    String jenis_kelamin = (data.getString("jenis_kelamin").equals("1") ? "laki Laki" : "Perempuan");
                                    String kelas = data.getString("kelas").trim();

                                    sessionManager.createSession(nama, email, foto, no_hp, nis, tempat_lahir, tanggal_lahir, jenis_kelamin, kelas);

                                        Toast.makeText(getApplicationContext(), "LOGIN SUKSES",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
                                        startActivity(intent);
                                        finish();

                                }else if (level.equals("guru")){

                                    String nama = data.getString("nama").trim();
                                    String email = data.getString("email").trim();
                                    String nis = data.getString("nip").trim();
                                    String foto = "";
                                    String no_hp = "";
                                    String tempat_lahir = "";
                                    String tanggal_lahir = "";
                                    String jenis_kelamin = "";
                                    String kelas = "";

                                    sessionManager.createSession(nama, email, foto, no_hp, nis, tempat_lahir, tanggal_lahir, jenis_kelamin, kelas);

                                        Toast.makeText(getApplicationContext(), "LOGIN SUKSES", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                        startActivity(intent);
                                        finish();
                                }
                            }else {
                                //jika login gagal
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Username atau Password Anda salah!")
                                        .setNegativeButton("Retry", null).create().show();

                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Username);
                params.put("password", Password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
