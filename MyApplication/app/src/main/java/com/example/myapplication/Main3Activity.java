package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.siswa.Dashboard;
import com.example.myapplication.siswa.Home;
import com.example.myapplication.siswa.Info;
import com.example.myapplication.siswa.Profil;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;

    SessionManager sessionManager;
    String nama,email;
    ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        qrcode = findViewById(R.id.qrcode);

        Intent intent = getIntent();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToogle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        View headerView = nvDrawer.getHeaderView(0);
        ImageView foto = (ImageView) headerView.findViewById(R.id.headerfoto);
        TextView nama = (TextView)  headerView.findViewById(R.id.headernama);
        TextView email = (TextView)  headerView.findViewById(R.id.headeremail);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String sNama = user.get(sessionManager.NAMA);
        String sEmail = user.get(sessionManager.EMAIL);

        nama.setText(sNama);
        email.setText(sEmail);
        Glide.with(getBaseContext())
                .load("http://smsbeb.mif-project.com/dashboard/siswa/generate/"+user.get(sessionManager.NIS))
                .into(qrcode);
        Log.d("nis",  user.get(sessionManager.NIS));

        Glide.with(getBaseContext())
                .load(user.get(sessionManager.FOTO))
                .into(foto);
    }
    public void selectItemDrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = Home.class;
                break;
            case R.id.profil:
                fragmentClass = Profil.class;
                break;
            case R.id.dashboard:
                fragmentClass = Dashboard.class;
                break;
            case R.id.info:
                fragmentClass = Info.class;
                break;
            default:
                fragmentClass = Dashboard.class;
        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout(View view) {
        Intent intent = new Intent(Main3Activity.this, LoginActivity.class);
        startActivity(intent);
    }


}
