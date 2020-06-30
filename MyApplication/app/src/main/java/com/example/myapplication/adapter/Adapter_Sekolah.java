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
import com.example.myapplication.model.Sekolah_Model;

import java.util.ArrayList;

public class Adapter_Sekolah extends RecyclerView.Adapter<Adapter_Sekolah.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Sekolah_Model> listdata2;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_Sekolah(Activity activity, ArrayList<Sekolah_Model> listdata2, Context context) {
        this.listdata2 = listdata2;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Sekolah.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_sekolah, parent, false);
        Adapter_Sekolah.ViewHolder vh = new Adapter_Sekolah.ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Sekolah.ViewHolder holder, int position) {
        final Adapter_Sekolah.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode2.setText(listdata2.get(position).getId_Sekolah());

        holder.tgl_sekolah.setText(listdata2.get(position).getTanggal_Sekolah());
        holder.jam_in.setText(listdata2.get(position).getJam());
        holder.no_tanggal.setText(listdata2.get(position).getTanggal_Sekolah());

        holder.status_absensekolah.setText(listdata2.get(position).getStatus_absen());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode2.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listdata2.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode2, tgl_sekolah, jam_in, status_absensekolah, no_tanggal;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode2 = (TextView) v.findViewById(R.id.kode2);

            tgl_sekolah = (TextView) v.findViewById(R.id.tgl_sekolah);
            jam_in = (TextView) v.findViewById(R.id.jam_in) ;
            no_tanggal = (TextView) v.findViewById(R.id.no_tanggal);

            status_absensekolah = (TextView) v.findViewById(R.id.status_absensekolah);
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

