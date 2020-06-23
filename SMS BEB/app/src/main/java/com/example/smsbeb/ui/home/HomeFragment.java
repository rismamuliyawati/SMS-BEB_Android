package com.example.smsbeb.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.smsbeb.R;
import com.example.smsbeb.fitur.Absen;
import com.example.smsbeb.fitur.Sukete;
import com.example.smsbeb.fitur.cbk;
import com.example.smsbeb.fitur.info;

public class HomeFragment extends Fragment {

    Button btnAbsen, btnSuket, btnCbK, btnIp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //Get ID tombol & Event Tombol
        btnAbsen = view.findViewById(R.id.btn_absensi);
        btnAbsen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Absen.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Membuka Menu Absen", Toast.LENGTH_SHORT).show();

            }
        });

        btnSuket = view.findViewById(R.id.btn_suket);
        btnSuket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sukete.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Membuka Menu Surat Keterangan", Toast.LENGTH_SHORT).show();

            }
        });

        btnCbK = view.findViewById(R.id.btnCBK);
        btnCbK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cbk.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Membuka Menu Catatan BK", Toast.LENGTH_SHORT).show();
            }
        });

        btnIp = view.findViewById(R.id.btnIP);
        btnIp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), info.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Membuka Menu Info Pembayaran", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
