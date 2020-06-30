package com.example.myapplication.siswa;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_Sekolah;
import com.example.myapplication.api.Url;
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
import java.util.Locale;
import java.util.Map;

public class FragmentPulang extends Fragment implements DatePickerListener {

//    HorizontalPicker picker;
//    TextView hasil;
//    SimpleDateFormat dateFormat;
//    SwipeRefreshLayout swipeHome;

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

    public FragmentPulang () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pulang_fragment,container,false);

//        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
//
//        picker = v.findViewById(R.id.datePicker2);
//        picker.setListener(this)
//                .setDays(120)
//                .setOffset(30)
//                .setDateSelectedColor(Color.DKGRAY)
//                .setDateSelectedTextColor(Color.WHITE)
//                .setMonthAndYearTextColor(Color.DKGRAY)
//                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
//                .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
//                .setTodayDateBackgroundColor(Color.GRAY)
//                .setUnselectedDayTextColor(Color.DKGRAY)
//                .setDayOfWeekTextColor(Color.DKGRAY)
//                .setUnselectedDayTextColor(getResources().getColor(R.color.orange))
//                .showTodayButton(false)
//                .init();
//        picker.setBackgroundColor(Color.LTGRAY);
//        picker.setDate(new DateTime());
//
//        swipeHome = v.findViewById(R.id.swlayout2);
//
//        swipeHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                picker.setDate(new DateTime());
//                if(swipeHome.isRefreshing()) {
//                    swipeHome.setRefreshing(false);
//                }
//            }
//        });
//
//        hasil = v.findViewById(R.id.result2);

        sessionManager = new SessionManager(getActivity());

        listdata2 = (RecyclerView) v.findViewById(R.id.listdata2);
        listdata2.setHasFixedSize(true);

        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Sekolah(getActivity(),(ArrayList<Sekolah_Model>) list, getContext());
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata2.setLayoutManager(mManager);
        listdata2.setAdapter(adapter);
        loadJson(); // your code

        return v;
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
//        Toast.makeText(this, dateSelected.toString(), Toast.LENGTH_SHORT).show();
//        hasil.setText(dateFormat.format(dateSelected.toDate()));
//        list.clear();
//        listdata2.getAdapter().notifyDataSetChanged();
    }

    private void loadJson()
    {
//        final HorizontalPicker picker = v.findViewById(R.id.datePicker2);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.PULANG, new Response.Listener<String>() {
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
//                                t+=data.getInt("point");
//                                data dari model nanti akan dimasukkan ke list model

                                list.add(md);
                            }

                            catch (Exception ea) {
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

}
