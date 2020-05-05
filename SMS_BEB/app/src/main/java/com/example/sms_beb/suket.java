package com.example.sms_beb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class suket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suket);

        getSupportActionBar().setTitle("Suket");
        getSupportActionBar().setSubtitle("Surat Keterangan");
    }

    public void pilihAlasan(View view) {
        Spinner spinnerPilihAlasan = findViewById(R.id.list_alasan);
        String Alasan = String.valueOf(spinnerPilihAlasan.getSelectedItem());
    }
}
