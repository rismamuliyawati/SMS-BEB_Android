package com.example.myapplication.guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_SuketGuru;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.SuketGuru_Model;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

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

public class SuketGuruActivity extends AppCompatActivity {

    ImageView fotosuket;
    TextInputEditText jenis_ket;
    private final int IMG_REQUEST = 1;
    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    private RequestQueue queue;

    String kelas;

    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_SuketGuru adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<SuketGuru_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata7;

    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suketguru);

        queue = Volley.newRequestQueue(SuketGuruActivity.this);

//        jenis_ket = (TextInputEditText) findViewById(R.id.jenis_ket);

        sessionManager = new SessionManager(this);

        //kelas = getIntent().getExtras().getString("kelas");
        Intent intent = getIntent();
        kelas = intent.getStringExtra("kelas");

        Log.d("kelas",kelas);

        listdata7 = (RecyclerView) findViewById(R.id.listdata7);
        listdata7.setHasFixedSize(true);
        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_SuketGuru(this,(ArrayList<SuketGuru_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata7.setLayoutManager(mManager);
        listdata7.setAdapter(adapter);
        loadJson();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadJson()
    {
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.SUKET_GURU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
                    JSONArray arr = res.getJSONArray("datasuketguru");
                    if(arr.length() > 0) {
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                SuketGuru_Model md = new SuketGuru_Model();
//                                memasukkan data dari api ke model
                                md.setId_suketg(data.getString("id"));
                                md.setTanggal_suketg(data.getString("tanggal"));
                                md.setJenis_suketg(data.getString("jenis"));
                                md.setFoto_suketg("http://smsbeb.mif-project.com/foto/surat/" + data.getString("foto"));
                                md.setNama_siswag(data.getString("nama_siswa"));
                                md.setKelasg(data.getString("kelas"));
                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal_suketg(date.format(d));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(SuketGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(SuketGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error "+e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SuketGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    mengirim request username dan password ke api
                params.put("nip", nis);
                params.put("kelas", kelas);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }

}
