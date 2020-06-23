package com.example.smsbeb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smsbeb.helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        membuat new request queue
        queue = Volley.newRequestQueue(this);

//        instance session manager
        sessionManager = new SessionManager(this);

//        apabila sudah login dan belum logout maka langsung diarahkan ke activity utama
        if (sessionManager.isLogin() == true){
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main);
            finish();
        }

//        mencari elemen by id
        textInputEmail = findViewById(R.id.textEmail);
        textInputPassword = findViewById(R.id.textPassword);

//        progress dialog instance
        progressDialog = new ProgressDialog(this);
    }

    //    fungsi validasi email
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("NIS tidak Boleh Kosong");
            return false;
        } else {
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    //    fungsi validasi password
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Password tidak boleh kosong");
            return false;
        } else {
            textInputPassword.setError(null);
            textInputPassword.setErrorEnabled(false);
            return true;
        }
    }

    private void _loginProcess() {
        progressDialog.setMessage("Sedang Memproses...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.API_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status == "false") {
                        Snackbar.make(findViewById(R.id.loginActivity), message, Snackbar.LENGTH_LONG).show();
                    } else {
                        JSONObject data = jsonObject.getJSONObject("data");

                        String id = data.getString("id_kelas");
                        String nis = data.getString("nis");
                        String nama = data.getString("nama");

                        sessionManager.createSession(nis, nama, id);

                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        main.putExtra("NIS", nis);
                        main.putExtra("NAMA", nama);
                        main.putExtra("ID_KELAS", id);
                        startActivity(main);
                        finish();
                    }
                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.loginActivity), e.toString(), Snackbar.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String message = "Terjadi error. Coba beberapa saat lagi.";

                if (error instanceof NetworkError){
                    message = "Tidak dapat terhubung ke internet. Harap periksa koneksi anda.";
                } else if (error instanceof AuthFailureError) {
                    message = "Gagal login. Harap periksa email dan password anda.";
                } else if (error instanceof ClientError) {
                    message = "Gagal login. Harap periksa email dan password anda.";
                } else if (error instanceof NoConnectionError){
                    message = "Tidak ada koneksi internet. Harap periksa koneksi anda.";
                } else if (error instanceof TimeoutError){
                    message = "Connection Time Out. Harap periksa koneksi anda.";
                }

                Snackbar.make(findViewById(R.id.loginActivity), message, Snackbar.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", textInputEmail.getEditText().getText().toString().trim());
                params.put("password", textInputPassword.getEditText().getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    //    fungsi u/ menjalankan konfirmasi form
    public void confirmInputLogin(View v) {
        if (validateEmail() | validatePassword()) {
            _loginProcess();
        }
    }

    private void initForgotPasswordTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textForgotPass);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>Forgot Your Password !!</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(intent);
            }
        });
    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
