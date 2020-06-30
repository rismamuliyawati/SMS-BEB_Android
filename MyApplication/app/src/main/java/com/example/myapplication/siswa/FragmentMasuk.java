package com.example.myapplication.siswa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_BK;
import com.example.myapplication.adapter.Adapter_Sekolah;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Bk_Model;
import com.example.myapplication.model.Sekolah_Model;
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
import java.util.Map;

public class FragmentMasuk extends Fragment implements DatePickerListener {

    View v ;

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_Sekolah adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Sekolah_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata2;
    //    TextView total;
    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    public FragmentMasuk () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.masuk_fragment,container,false);

        sessionManager = new SessionManager(getActivity());

        RecyclerView listdata2 = (RecyclerView) v.findViewById(R.id.listdata2);
        listdata2.setHasFixedSize(true);

        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Sekolah(getActivity(),(ArrayList<Sekolah_Model>) list, getContext());
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata2.setLayoutManager(mManager);
        listdata2.setAdapter(adapter);
//        total = findViewById(R.id.total);
        loadJson();

        return v;
    }

    private void loadJson()
    {
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.MASUK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
//                    total.setText(res.getString("point"));
                    JSONArray arr = res.getJSONArray("dataabsen");
                    if(arr.length() > 0) {
//                        int t = 0;
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Sekolah_Model md = new Sekolah_Model();
//                                memasukkan data dari api ke model
                                md.setId_Sekolah(data.getString("id"));
                                md.setTanggal_Sekolah(data.getString("tanggal"));
                                md.setStatus_absen(data.getString("status_absen"));
                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal_Sekolah(date.format(d));
                                    md.setJam(time.format(d));
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
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error "+e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }
}
