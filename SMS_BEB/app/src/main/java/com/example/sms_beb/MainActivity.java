package com.example.sms_beb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import com.example.sms_beb.fitur.Absen;
import com.example.sms_beb.fitur.Sukete;
import com.example.sms_beb.fitur.cbk;
import com.example.sms_beb.fitur.info;

public class MainActivity extends AppCompatActivity {

    Button btnAbsen, btnSuket, btnCbK, btnIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get ID tombol & Event Tombol
        btnAbsen = (Button) findViewById(R.id.btn_absensi);
        btnAbsen.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Absen.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Membuka Menu Absen", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        btnSuket = (Button) findViewById(R.id.btn_suket);
        btnSuket.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sukete.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Membuka Menu Surat Keterangan", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        btnCbK = (Button) findViewById(R.id.btnCBK);
        btnCbK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cbk.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Membuka Menu Catatan BK", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnIp = (Button) findViewById(R.id.btnIP);
        btnIp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, info.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Membuka Menu Info Pembayaran", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
