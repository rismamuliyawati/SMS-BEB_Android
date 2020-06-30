package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.myapplication.R;
import com.example.myapplication.model.Bk_Model;
import com.example.myapplication.model.Suket_Model;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Suket extends RecyclerView.Adapter<Adapter_Suket.ViewHolder> {
    //    mengambil list dari model barang
    private ArrayList<Suket_Model> listdata3;
    //    menentukan activity
    private Activity activity;
    private Context context;
    Dialog dialog;
    //    constructor dari adapterbarang
    public Adapter_Suket(Activity activity, ArrayList<Suket_Model> listdata3, Context context) {
        this.listdata3 = listdata3;
        this.activity = activity;
        this.context = context;
        dialog = new Dialog(context);
    }

    @Override
    public Adapter_Suket.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        menentukan view untuk listdata disini kita menggunakan layout dengan nama template barang
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_suket, parent, false);
        final Adapter_Suket.ViewHolder vh = new Adapter_Suket.ViewHolder(v);

        dialog.setContentView(R.layout.popup_suket);

        vh.foto_suket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView popup_foto = (ImageView) dialog.findViewById(R.id.popup_foto);
                Button btn_close = (Button) dialog.findViewById(R.id.btn_close);

//                popup_foto.Glide(listdata3.get(vh.getAdapterPosition()).getFoto_suket());

                Glide.with(activity)
                        .load(listdata3.get(vh.getAdapterPosition()).getFoto_suket ())
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
    public void onBindViewHolder(Adapter_Suket.ViewHolder holder, int position) {
        final Adapter_Suket.ViewHolder x = holder;
//        mengambil data dari model yang sudah di set tadi di activity dengan menentukan posisinya
        holder.kode3.setText(listdata3.get(position).getId_suket());
        holder.jenis_suket.setText(listdata3.get(position).getJenis_suket());
        holder.tanggal_suket.setText(listdata3.get(position).getTanggal_suket());
//        holder.foto_suket.setImageView(listdata3.get(position).getFoto_suket());
//        fungsi glide dibawah adalah memuat gambar yang sudah tersedia menggunakan link
        holder.mContext = context;
//        fungsi dibawah ini untuk menyembunyikan kode
        holder.kode3.setVisibility(View.GONE);

        Glide.with(activity)
                .load(listdata3.get(position).getFoto_suket ())
                .into(holder.foto_suket);
    }

    @Override
    public int getItemCount() {
        return listdata3.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        //        deklarasi text di view
        private TextView kode3, jenis_suket, tanggal_suket;
        private ImageView foto_suket ;
        Context mContext;

        public ViewHolder(View v) {
            super(v);
//            final ImagePopup imagePopup = new ImagePopup(V);
//            imagePopup.setWindowHeight(800); // Optional
//            imagePopup.setWindowWidth(800); // Optional
//            imagePopup.setBackgroundColor(Color.BLACK);  // Optional
//            imagePopup.setFullScreen(true); // Optional
//            imagePopup.setHideCloseIcon(true);  // Optional
//            imagePopup.setImageOnClickClose(true);  // Optional
//
//            ImageView imageView = (ImageView) v.findViewById(R.id.foto_suket);
//
//            imagePopup.initiatePopupWithGlide("http://192.168.43.173/Absensi_MIF-Codeigniter/foto/surat/"); // Load Image from Drawable
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    /** Initiate Popup view **/
//                    imagePopup.viewPopup();
//
//                }
//            });

//            inisialisasi view
            kode3 = (TextView) v.findViewById(R.id.kode3);
            jenis_suket = (TextView) v.findViewById(R.id.jenis_suket);
            tanggal_suket = (TextView) v.findViewById(R.id.tanggal_suket);
            foto_suket = (ImageView) v.findViewById(R.id.foto_suket);
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

