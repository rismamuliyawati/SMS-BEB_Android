package com.example.myapplication.guru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_DataAbsenMapel;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.DataAbsenMapel_Model;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputAbsenGuruActivity extends AppCompatActivity {

    ImageView fotosuket;
    TextInputEditText jenis_ket;
    private final int IMG_REQUEST = 1;
    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    private RequestQueue queue;

    String id_kelas;

    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_DataAbsenMapel adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<DataAbsenMapel_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata8;

    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_absen_guru);

        sessionManager = new SessionManager(this);
        Intent intent = getIntent();
        id_kelas = intent.getStringExtra("id_kelas");

        Log.d("id_kelas",id_kelas);

        listdata8 = (RecyclerView) findViewById(R.id.listdata8);
        listdata8.setHasFixedSize(true);
        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_DataAbsenMapel(this,(ArrayList<DataAbsenMapel_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata8.setLayoutManager(mManager);
        listdata8.setAdapter(adapter);
        loadJson();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadJson()
    {
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.DATA_ABSEN_MAPEL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
                    JSONArray arr = res.getJSONArray("absenmapel");
                    if(arr.length() > 0) {
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                DataAbsenMapel_Model md = new DataAbsenMapel_Model();
//                                memasukkan data dari api ke model
                                md.setId_Absenmapel(data.getString("id"));
                                md.setSiswamapel(data.getString("siswa"));
                                md.setId_kelasmapel(data.getString("id_kelas"));
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(InputAbsenGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(InputAbsenGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error "+e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InputAbsenGuruActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    mengirim request username dan password ke api
                params.put("nip", nis);
                params.put("id_kelas", id_kelas);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }
}
