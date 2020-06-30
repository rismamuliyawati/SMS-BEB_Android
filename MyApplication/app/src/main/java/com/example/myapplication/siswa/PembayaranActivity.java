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
import com.example.myapplication.adapter.Adapter_Pembayaran;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Pembayaran_Model;

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

public class PembayaranActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_Pembayaran adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Pembayaran_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata1;
    TextView jenis_pembayaran;
    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);

        listdata1 = (RecyclerView) findViewById(R.id.listdata1);
        listdata1.setHasFixedSize(true);

//      inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Pembayaran(this,(ArrayList<Pembayaran_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata1.setLayoutManager(mManager);
        listdata1.setAdapter(adapter);
        jenis_pembayaran = findViewById(R.id.jenis_pembayaran);
        loadJson();

    }

    private void loadJson()
    {
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis = user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.PEMBAYARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
//                  total.setText(res.getString("point"));
                    JSONArray arr = res.getJSONArray("databayar");
                    if (arr.length() > 0) {
//                        int t = 0;
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Pembayaran_Model md = new Pembayaran_Model();
//                                memasukkan data dari api ke model
                                md.setId_Pembayaran(data.getString("id"));
                                md.setTanggal_Pembayaran(data.getString("tanggal"));
//                                md.setJenis_Pembayaran(data.getString("jenis_pembayaran"));
                                md.setJml_tagihan(data.getString("jml_tagihan"));
                                md.setStatus(data.getString("status"));
                                md.setDeskripsi_Pembayaran(data.getString("deskripsi"));

                                if (data.getString("jenis_pembayaran").equals("1")){
                                    md.setJenis_Pembayaran("SPP");
                                } else {
                                    md.setJenis_Pembayaran("Study Tour");
                                }

                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal_Pembayaran(date.format(d));
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
                    } else {
                        Toast.makeText(PembayaranActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(PembayaranActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error " + e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PembayaranActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {
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
}
