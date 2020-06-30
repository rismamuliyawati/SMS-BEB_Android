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
import com.example.myapplication.model.Mapel_Model;

import java.util.ArrayList;

public class Adapter_Mapel extends RecyclerView.Adapter<Adapter_Mapel.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Mapel_Model> listdata4;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_Mapel(Activity activity, ArrayList<Mapel_Model> listdata4, Context context) {
        this.listdata4 = listdata4;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Mapel.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_mapel, parent, false);
        Adapter_Mapel.ViewHolder vh = new Adapter_Mapel.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Mapel.ViewHolder holder, int position) {
        final Adapter_Mapel.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode4.setText(listdata4.get(position).getId_Absenmapel());
        holder.nama_guru.setText(listdata4.get(position).getNama_guru());
        holder.nama_mapel.setText(listdata4.get(position).getNama_mapel());
        holder.jam_mapel.setText(listdata4.get(position).getJam_mapel());
        holder.status_absenmapel.setText(listdata4.get(position).getStatus_absenmapel());
        holder.tanggal_mapel.setText(listdata4.get(position).getTanggal_mapel());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode4.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listdata4.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode4, nama_guru, nama_mapel, jam_mapel, status_absenmapel, tanggal_mapel;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode4 = (TextView) v.findViewById(R.id.kode4);
            nama_guru = (TextView) v.findViewById(R.id.nama_guru);
            nama_mapel = (TextView) v.findViewById(R.id.nama_mapel);
            jam_mapel = (TextView) v.findViewById(R.id.jam_mapel);
            status_absenmapel = (TextView) v.findViewById(R.id.status_mapel);
            tanggal_mapel = (TextView) v.findViewById(R.id.tanggal_mapel);
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

