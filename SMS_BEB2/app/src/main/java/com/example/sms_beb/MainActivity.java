package com.example.sms_beb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button btnAbsen,btnSuket,btnCbK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get ID tombol & Event Tombol
        btnAbsen= (Button) findViewById(R.id.btn_absensi);
        btnAbsen.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Membuka Menu Absen", Toast.LENGTH_SHORT).show();

            } });

        btnSuket= (Button) findViewById(R.id.btn_suket);
        btnSuket.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent suket = new Intent(MainActivity.this, suket.class);
                Toast.makeText(getApplicationContext(), "Membuka Menu Surat Keterangan", Toast.LENGTH_SHORT).show();

            } });

        btnCbK= (Button) findViewById(R.id.btnCBK);
        btnCbK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Membuka Menu Catatan BK", Toast.LENGTH_SHORT).show();

            } });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_samping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

