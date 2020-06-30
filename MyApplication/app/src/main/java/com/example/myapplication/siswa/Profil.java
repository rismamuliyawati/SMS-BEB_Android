package com.example.myapplication.siswa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;

import java.util.HashMap;

public class Profil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SessionManager sessionManager;
    String nama, email, no_hp, nis, tempat_lahir, tanggal_lahir, jenis_kelamin, id_kelas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_profil, container, false);

         sessionManager = new SessionManager(getActivity());
         sessionManager.checkLogin();

         TextView nama = (TextView)  view.findViewById(R.id.namasiswa);
         TextView email = (TextView)  view.findViewById(R.id.emailsiswa);
         TextView no_hp = (TextView)  view.findViewById(R.id.hpsiswa);
         TextView nis = (TextView)  view.findViewById(R.id.nis);
         TextView tempat_lahir = (TextView)  view.findViewById(R.id.tempat_lahir);
         TextView tanggal_lahir = (TextView)  view.findViewById(R.id.tanggal_lahir);
         TextView jenis_kelamin = (TextView)  view.findViewById(R.id.jenis_kelamin);
         TextView id_kelas = (TextView)  view.findViewById(R.id.kelas);
         ImageView foto = view.findViewById(R.id.foto);

         HashMap<String, String> user = sessionManager.getUserDetail();

         String Nama = user.get(sessionManager.NAMA);
         String Email = user.get(sessionManager.EMAIL);
         String No_hp = user.get(sessionManager.NO_HP);
         String Nis = user.get(sessionManager.NIS);
         String Tempat_lahir = user.get(sessionManager.TEMPAT_LAHIR);
         String Tanggal_lahir = user.get(sessionManager.TANGGAL_LAHIR);
         String Jenis_kelamin = user.get(sessionManager.JENIS_KELAMIN);
         String Id_kelas = user.get(sessionManager.ID_KELAS);

         nama.setText(Nama);
         email.setText(Email);
         no_hp.setText(No_hp);
         nis.setText(Nis);
         tempat_lahir.setText(Tempat_lahir);
         tanggal_lahir.setText(Tanggal_lahir);
         jenis_kelamin.setText(Jenis_kelamin);
         id_kelas.setText(Id_kelas);
         Glide.with(getContext())
                .load(user.get(sessionManager.FOTO))
                .into(foto);

         return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Profil() {

    }

    public static Profil newInstance(String param1, String param2) {
        Profil fragment = new Profil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
