package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Bk_Model;
import com.example.myapplication.model.Jadwal_Model;

import java.util.ArrayList;

public class Adapter_Jadwal extends RecyclerView.Adapter<Adapter_Jadwal.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Jadwal_Model> listdata5;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_Jadwal(Activity activity, ArrayList<Jadwal_Model> listdata5, Context context) {
        this.listdata5 = listdata5;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Jadwal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_jadwal, parent, false);
        Adapter_Jadwal.ViewHolder vh = new Adapter_Jadwal.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Jadwal.ViewHolder holder, int position) {
        final Adapter_Jadwal.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode5.setText(listdata5.get(position).getId_jadwal());
        holder.hari.setText(listdata5.get(position).getHari());
        holder.nama_guru.setText(listdata5.get(position).getGuru());
        holder.kelas.setText(listdata5.get(position).getKelas());
        holder.nama_mapel.setText(listdata5.get(position).getMapel());
        holder.jadwal_jam.setText(listdata5.get(position).getJam());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode5.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listdata5.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode5, hari, nama_guru, kelas, nama_mapel, jadwal_jam;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode5 = (TextView) v.findViewById(R.id.kode5);
            hari = (TextView) v.findViewById(R.id.hari);
            nama_guru = (TextView) v.findViewById(R.id.guru);
            kelas = (TextView) v.findViewById(R.id.kelas);
            nama_mapel = (TextView) v.findViewById(R.id.mapel);
            jadwal_jam = (TextView) v.findViewById(R.id.jam);
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

