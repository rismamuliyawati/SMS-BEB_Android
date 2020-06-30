package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Bk_Model;

import java.util.ArrayList;

public class Adapter_BK extends RecyclerView.Adapter<Adapter_BK.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Bk_Model> listdata;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_BK(Activity activity, ArrayList<Bk_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_BK.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_bk, parent, false);
        Adapter_BK.ViewHolder vh = new Adapter_BK.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_BK.ViewHolder holder, int position) {
        final Adapter_BK.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode.setText(listdata.get(position).getId_bk());
        holder.deskripsi.setText(listdata.get(position).getDeskripsi());
        holder.tanggal.setText(listdata.get(position).getTanggal());
        holder.point.setText(listdata.get(position).getPoint());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode, deskripsi, point, tanggal;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode = (TextView) v.findViewById(R.id.kode);
            deskripsi = (TextView) v.findViewById(R.id.deskripsi);
            point = (TextView) v.findViewById(R.id.point);
            tanggal = (TextView) v.findViewById(R.id.tanggal);
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

