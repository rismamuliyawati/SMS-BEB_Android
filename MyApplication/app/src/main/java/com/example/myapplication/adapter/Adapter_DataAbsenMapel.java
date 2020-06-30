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
import com.example.myapplication.model.DataAbsenMapel_Model;

import java.util.ArrayList;

public class Adapter_DataAbsenMapel extends RecyclerView.Adapter<Adapter_DataAbsenMapel.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<DataAbsenMapel_Model> listdata8;
    //    menentukan activity
    private Activity activity;
    private Context context;
    //    constructor dari adapterbarang
    public Adapter_DataAbsenMapel(Activity activity, ArrayList<DataAbsenMapel_Model> listdata8, Context context) {
        this.listdata8 = listdata8;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_DataAbsenMapel.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_input_absen, parent, false);
        Adapter_DataAbsenMapel.ViewHolder vh = new Adapter_DataAbsenMapel.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_DataAbsenMapel.ViewHolder holder, int position) {
        final Adapter_DataAbsenMapel.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode8.setText(listdata8.get(position).getId_Absenmapel());
        holder.siswa.setText(listdata8.get(position).getSiswamapel());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode8.setVisibility(View.GONE);

        final String kelas = listdata8.get(position).getId_kelasmapel();
    }

    @Override
    public int getItemCount() {
        return listdata8.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode8, siswa;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            inisialisasi view
            kode8 = (TextView) v.findViewById(R.id.kode8);
            siswa = (TextView) v.findViewById(R.id.siswamapel);
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

