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
import com.example.myapplication.model.Pembayaran_Model;

import java.util.ArrayList;

public class Adapter_Pembayaran extends RecyclerView.Adapter<Adapter_Pembayaran.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Pembayaran_Model> listdata1;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_Pembayaran(Activity activity, ArrayList<Pembayaran_Model> listdata1, Context context) {
        this.listdata1 = listdata1;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pembayaran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_pembayaran, parent, false);
        Adapter_Pembayaran.ViewHolder vh = new Adapter_Pembayaran.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pembayaran.ViewHolder holder, int position) {
        final Adapter_Pembayaran.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode1.setText(listdata1.get(position).getId_Pembayaran());
        holder.tgl_pembayaran.setText(listdata1.get(position).getTanggal_Pembayaran());
        holder.jenis_pembayaran.setText(listdata1.get(position).getJenis_Pembayaran());
        holder.desc_pembayaran.setText(listdata1.get(position).getDeskripsi_Pembayaran());
        holder.jml_pembayaran.setText(listdata1.get(position).getJml_tagihan());
        holder.status_pembayaran.setText(listdata1.get(position).getStatus());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode1.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listdata1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode1, tgl_pembayaran, jenis_pembayaran, desc_pembayaran, jml_pembayaran, status_pembayaran;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode1 = (TextView) v.findViewById(R.id.kode1);
            tgl_pembayaran = (TextView) v.findViewById(R.id.tgl_pembayaran);
            jenis_pembayaran = (TextView) v.findViewById(R.id.jenis_pembayaran);
            desc_pembayaran = (TextView) v.findViewById(R.id.desc_pembayaran);
            jml_pembayaran = (TextView) v.findViewById(R.id.jml_pembayaran);
            status_pembayaran = (TextView) v.findViewById(R.id.status_pembayaran);
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

