package com.example.myapplication.guru;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.myapplication.adapter.Adapter_Jadwal;
import com.example.myapplication.adapter.Adapter_Sekolah;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Jadwal_Model;
import com.example.myapplication.model.Sekolah_Model;

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

public class JadwalFragment extends Fragment {

    View v ;

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_Jadwal adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Jadwal_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata5;
    //    TextView total;
    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    public JadwalFragment () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_jadwal,container,false);

        sessionManager = new SessionManager(getActivity());

        RecyclerView listdata5 = (RecyclerView) v.findViewById(R.id.listdata5);
        listdata5.setHasFixedSize(true);

        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Jadwal(getActivity(),(ArrayList<Jadwal_Model>) list, getContext());
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata5.setLayoutManager(mManager);
        listdata5.setAdapter(adapter);
        loadJson();

        return v;
    }

    private void loadJson()
    {
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.JADWAL_GURU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
//                    total.setText(res.getString("point"));
                    JSONArray arr = res.getJSONArray("datajadwalguru");
                    if(arr.length() > 0) {
//                        int t = 0;
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Jadwal_Model md = new Jadwal_Model();
//                                memasukkan data dari api ke model
                                md.setId_jadwal(data.getString("id"));
                                md.setHari(data.getString("hari"));
                                md.setGuru(data.getString("guru"));
                                md.setKelas(data.getString("kelas"));
                                md.setMapel(data.getString("mapel"));
                                md.setJam(data.getString("jam"));
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
                params.put("nip", nis);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }
}
