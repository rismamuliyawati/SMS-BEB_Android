package com.example.myapplication.siswa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.content.DialogInterface;
import android.os.Build;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.adapter.Adapter_Suket;
import com.example.myapplication.api.Url;
import com.example.myapplication.model.Suket_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuketActivity extends AppCompatActivity {

    String nis;

    ImageView fotosuket;
    TextInputEditText jenis_ket;
    private final int IMG_REQUEST = 1;
    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    private RequestQueue queue;

    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;

    //    fungsi adapter adalah jembatan antara dan AdapterView (contohnya ListView) dengan data
    private Adapter_Suket adapter;
    //    fungsi model adalah untuk menentukan data yang mau diambil dan di setting di model
    private List<Suket_Model> list;
    //    recycler view adalah sebuah wadah untuk menampilkna data
    private RecyclerView listdata3;

    RecyclerView.LayoutManager mManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suket);

        queue = Volley.newRequestQueue(SuketActivity.this);

//        jenis_ket = (TextInputEditText) findViewById(R.id.jenis_ket);

        sessionManager = new SessionManager(this);

        listdata3 = (RecyclerView) findViewById(R.id.listdata3);
        listdata3.setHasFixedSize(true);
        //        inisialisasi arraylist
        list = new ArrayList<>();
        adapter = new Adapter_Suket(this,(ArrayList<Suket_Model>) list, this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata3.setLayoutManager(mManager);
        listdata3.setAdapter(adapter);
        loadJson();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();

            }
        });

    }

    private void loadJson()
    {
        Intent data = getIntent();
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
//        melakukan request dari android ke server untuk mengambil data menggunakan method get
        StringRequest senddata = new StringRequest(Request.Method.POST, Url.SUKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
//                     fungsi ini berfungsi untuk mengubah string menjadi jsonObject
                    res = new JSONObject(response);
//                    mengambil raay dengan nama data dan ditampung di arr
                    JSONArray arr = res.getJSONArray("dataketerangan");
                    if(arr.length() > 0) {
//                        melakukan looping data di array data sesuai dengan jumlah data
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
//                              membuat object barangmodel untuk set dan get nantinya
                                Suket_Model md = new Suket_Model();
//                                memasukkan data dari api ke model
                                md.setId_suket(data.getString("id"));
                                md.setTanggal_suket(data.getString("tanggal"));
                                md.setJenis_suket(data.getString("jenis"));
                                md.setFoto_suket("http://smsbeb.mif-project.com/foto/surat/" + data.getString("foto"));
                                try {
                                    DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date d = f.parse(data.getString("tanggal"));
                                    DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                                    System.out.println("Date: " + date.format(d));
                                    System.out.println("Time: " + time.format(d));
                                    md.setTanggal_suket(date.format(d));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(SuketActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    Toast.makeText(SuketActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    Log.d("pesan", "error "+e.getMessage());
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SuketActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    mengirim request username dan password ke api
                params.put("nis", nis);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }



    private void showBottomSheetDialog() {
        View view;
        view = getLayoutInflater().inflate(R.layout.input_suket, null);

        queue = Volley.newRequestQueue(SuketActivity.this);
        sessionManager = new SessionManager(SuketActivity.this);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

//        jenis_ket = (TextInputEditText) view.findViewById(R.id.jenis_ket);
        fotosuket = (ImageView) view.findViewById(R.id.fotosuket);

        (view.findViewById(R.id.btn_upload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });

        (view.findViewById(R.id.btn_kirimsuket)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInputUpload();
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadSuket() {
        View view;
        view = getLayoutInflater().inflate(R.layout.input_suket, null);

        HashMap<String, String> user = sessionManager.getUserDetail();
        final String nis =  user.get(sessionManager.NIS);
        final Spinner Ket = view.findViewById(R.id.sp_name);

        progressDialog = new ProgressDialog(SuketActivity.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://smsbeb.mif-project.com/Api/tes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");
                    progressDialog.dismiss();
                    if(status.equals("true")){
                        Intent intent = new Intent(SuketActivity.this, SuketActivity.class);
                        intent.putExtra("nis",nis);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SuketActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuketActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("input_suket", Ket.getSelectedItem().toString().trim());
                params.put("foto", imageToString(bitmap));
                params.put("nis", nis);

                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == SuketActivity.this.RESULT_OK && data != null) {
//            mengambil alamat file gambar
            Uri path = data.getData();

            try {
                InputStream inputStream = SuketActivity.this.getContentResolver().openInputStream(path);
                mBitmapName = path.getPath();
                bitmap = BitmapFactory.decodeStream(inputStream);
                fotosuket.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                Toast.makeText(SuketActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateFoto() {
        if (bitmap==null) {
            Toast.makeText(SuketActivity.this, "Gambar harus dipilih", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void confirmInputUpload() {
        if (validateFoto()) {
            uploadSuket();
        }
    }
}
