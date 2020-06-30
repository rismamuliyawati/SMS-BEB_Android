package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.SuketGuru_Model;
import com.example.myapplication.model.Suket_Model;

import java.util.ArrayList;

public class Adapter_SuketGuru extends RecyclerView.Adapter<Adapter_SuketGuru.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<SuketGuru_Model> listdata7;
    //    menentukan activity
    private Activity activity;
    private Context context;
    Dialog dialog;
    //    constructor dari adapterbarang
    public Adapter_SuketGuru(Activity activity, ArrayList<SuketGuru_Model> listdata7, Context context) {
        this.listdata7 = listdata7;
        this.activity = activity;
        this.context = context;
        dialog = new Dialog(context);
    }

    @Override
    public Adapter_SuketGuru.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_suketguru, parent, false);
        final Adapter_SuketGuru.ViewHolder vh = new Adapter_SuketGuru.ViewHolder(v);

        dialog.setContentView(R.layout.popup_suketguru);

        vh.foto_suket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView popup_foto = (ImageView) dialog.findViewById(R.id.popup_foto2);
                Button btn_close = (Button) dialog.findViewById(R.id.btn_close2);

//                popup_foto.Glide(listdata3.get(vh.getAdapterPosition()).getFoto_suket());

                Glide.with(activity)
                        .load(listdata7.get(vh.getAdapterPosition()).getFoto_suketg ())
                        .into(popup_foto);

                dialog.show();

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_SuketGuru.ViewHolder holder, int position) {
        final Adapter_SuketGuru.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode7.setText(listdata7.get(position).getId_suketg());
        holder.jenis_suket.setText(listdata7.get(position).getJenis_suketg());
        holder.tanggal_suket.setText(listdata7.get(position).getTanggal_suketg());
        holder.kelas.setText(listdata7.get(position).getKelasg());
        holder.nama_siswa.setText(listdata7.get(position).getNama_siswag());
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode7.setVisibility(View.GONE);

        Glide.with(activity)
                .load(listdata7.get(position).getFoto_suketg ())
                .into(holder.foto_suket);
    }

    @Override
    public int getItemCount() {
        return listdata7.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode7, jenis_suket, tanggal_suket, nama_siswa, kelas;
        private ImageView foto_suket ;
        Context mContext;

        public ViewHolder(View v) {
            super(v);

//            inisialisasi view
            kode7 = (TextView) v.findViewById(R.id.kode7);
            jenis_suket = (TextView) v.findViewById(R.id.jenis_suketg);
            tanggal_suket = (TextView) v.findViewById(R.id.tanggal_suketg);
            foto_suket = (ImageView) v.findViewById(R.id.foto_suketg);
            nama_siswa = (TextView) v.findViewById(R.id.namasiswag);
            kelas = (TextView) v.findViewById(R.id.kelas_g);
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

