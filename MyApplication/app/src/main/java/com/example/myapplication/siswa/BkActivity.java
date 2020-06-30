package com.example.myapplication.siswa;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_BK;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Bk_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BkActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_BK adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Bk_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata;
    TextView total;
    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bk);


        sessionManager = new SessionManager(this);

        listdata = (RecyclerView) findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
//        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_BK(this,(ArrayList<Bk_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        total = findViewById(R.id.total);
        loadJson();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadJson()
    {
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.BK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
                    total.setText(res.getString("point"));
                    JSONArray arr = res.getJSONArray("databk");
                    if(arr.length() > 0) {
//                        int t = 0;
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Bk_Model md = new Bk_Model();
//                                memasukkan data dari api ke model
                                md.setId_bk(data.getString("id_bk"));
                                md.setTanggal(data.getString("tanggal"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setPoint(data.getString("point"));
                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal(date.format(d));
//                                    md.setJam(time.format(d));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();
                            }
                        }
//                        total.setText(t);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(BkActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(BkActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error "+e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BkActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    mengirim request username dan password ke api
                params.put("nis", nis);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }

//    private void Bk(final String Nis) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BK,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getBoolean("status")) {
//                                JSONObject databk = jsonObject.getJSONObject("data");
//
//                                String tanggal= databk.getString("tanggal").trim();
//                                String deskripsi= databk.getString("deskripsi").trim();
//
//                                sessionManager.createSession(nama, email, foto, no_hp, nis, tempat_lahir, tanggal_lahir, jenis_kelamin, kelas);
//
//                                String level = jsonObject.getString("level");
//                                if (level.equals("siswa")){
//                                    Toast.makeText(getApplicationContext(), "LOGIN SUKSES",Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }else if (level.equals("guru")){
//                                    Toast.makeText(getApplicationContext(), "LOGIN SUKSES", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }else {
//                                //jika login gagal
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("Username atau Password Anda salah!")
//                                        .setNegativeButton("Retry", null).create().show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(LoginActivity.this, "Error" +e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", Username);
//                params.put("password", Password);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

}
