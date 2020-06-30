package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.guru.InputAbsenGuruActivity;
import com.example.myapplication.R;
import com.example.myapplication.guru.SuketGuruActivity;
import com.example.myapplication.model.InputAbsen_Model;

import java.util.ArrayList;

public class Adapter_InputAbsen extends RecyclerView.Adapter<Adapter_InputAbsen.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<InputAbsen_Model> listdata6;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_InputAbsen(Activity activity, ArrayList<InputAbsen_Model> listdata6, Context context) {
        this.listdata6 = listdata6;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_InputAbsen.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_absensimapel, parent, false);
        Adapter_InputAbsen.ViewHolder vh = new Adapter_InputAbsen.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_InputAbsen.ViewHolder holder, int position) {
        final Adapter_InputAbsen.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode6.setText(listdata6.get(position).getId_Abseng());
        holder.nama_mapel.setText(listdata6.get(position).getMapelg());
        holder.nama_kelas.setText(listdata6.get(position).getKelasg());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode6.setVisibility(View.GONE);

        final String kelas = listdata6.get(position).getId_kelasg();

        holder.suketg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuketGuruActivity.class);
                intent.putExtra("kelas", kelas);
                activity.startActivity(intent);
            }
        });

        holder.abseng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InputAbsenGuruActivity.class);
                intent.putExtra("id_kelas", kelas);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata6.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode6, nama_mapel, nama_kelas;
        RelativeLayout suketg, abseng;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode6 = (TextView) v.findViewById(R.id.kode6);
            nama_mapel = (TextView) v.findViewById(R.id.mapelg);
            nama_kelas = (TextView) v.findViewById(R.id.kelasg);
            suketg = (RelativeLayout) v.findViewById(R.id.suketg);
            abseng = (RelativeLayout) v.findViewById(R.id.abseng);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent;
//                      mengarahkan halaman ketika di klik di data barang
//                        intent = new Intent(v.getContext(), Form_Sewa.class);
//                        intent.putExtra("kode", kode.getText().toString());
//                        intent.putExtra("nama", nama.getText().toString());
//                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }

}

