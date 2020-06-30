package com.example.myapplication.siswa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.myapplication.adapter.Adapter_Mapel;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Mapel_Model;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
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
import java.util.Locale;
import java.util.Map;

public class KelasActivity extends AppCompatActivity implements DatePickerListener{

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_Mapel adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Mapel_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata4;
    HorizontalPicker picker;
    TextView hasil;
    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;
    SimpleDateFormat dateFormat;
    SwipeRefreshLayout swipeHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

//        this.mHandler = new Handler();
//        this.mHandler.postDelayed(m_Runnable,5000);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

        picker = findViewById(R.id.datePicker1);
        picker.setListener(this)
                .setDays(120)
                .setOffset(30)
                .setDateSelectedColor(Color.DKGRAY)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.DKGRAY)
                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(Color.GRAY)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY)
                .setUnselectedDayTextColor(getResources().getColor(R.color.orange))
                .showTodayButton(false)
                .init();
        picker.setBackgroundColor(Color.LTGRAY);
        picker.setDate(new DateTime());

        swipeHome = findViewById(R.id.swlayout4);

        swipeHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                picker.setDate(new DateTime());
                if(swipeHome.isRefreshing()) {
                    swipeHome.setRefreshing(false);
                }
            }
        });

        hasil = findViewById(R.id.result);
        sessionManager = new SessionManager(this);

        listdata4 = (RecyclerView) findViewById(R.id.listdata4);
        listdata4.setHasFixedSize(true);

        //      inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Mapel(this,(ArrayList<Mapel_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata4.setLayoutManager(mManager);
        listdata4.setAdapter(adapter);

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
//        Toast.makeText(this, dateSelected.toString(), Toast.LENGTH_SHORT).show();
        hasil.setText(dateFormat.format(dateSelected.toDate()));
        list.clear();
        loadJson(); // your code
        listdata4.getAdapter().notifyDataSetChanged();
    }

    private void loadJson()
    {
        final HorizontalPicker picker = findViewById(R.id.datePicker1);
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis = user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.MAPEL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
//                  total.setText(res.getString("point"));
                    JSONArray arr = res.getJSONArray("datamapel");
                    if (arr.length() > 0) {
//                        int t = 0;
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Mapel_Model md = new Mapel_Model();
//                                memasukkan data dari api ke model
                                md.setId_Absenmapel(data.getString("id"));
                                md.setNama_guru(data.getString("guru"));
                                md.setNama_mapel(data.getString("mapel"));
                                md.setStatus_absenmapel(data.getString("status_absen"));
                                md.setTanggal_mapel(data.getString("tanggal"));
                                md.setJam_mapel(data.getString("jam"));
                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
//                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal_mapel(date.format(d));
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
                        Toast.makeText(KelasActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(KelasActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error " + e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KelasActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    mengirim request username dan password ke api
                params.put("nis", nis);
                params.put("tanggal", hasil.getText().toString().trim());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }

}
