package com.example.sms_beb.fitur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sms_beb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class cbk extends AppCompatActivity {

    ImageView imgorang;
    TextView txtnama, txtnis, txtket;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbk);

        String url = "http://192.168.43.128/cbk/getdata.php"; //sesuaikan dengan ip pc anda
        imgorang = (ImageView) findViewById(R.id.imgorang);
        txtnama = (TextView) findViewById(R.id.txtnama);
        txtnis = (TextView) findViewById(R.id.txtnis);
        txtket = (TextView) findViewById(R.id.txtket);

        requestQueue = Volley.newRequestQueue(cbk.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("cbk");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id"));
                        map.put("nama", json.getString("nama"));
                        map.put("gambar", json.getString("gambar"));
                        map.put("nis", json.getString("nis"));
                        map.put("ket", json.getString("ket"));
                        list_data.add(map);
                    }
                    Glide.with(getApplicationContext())
                            .load("http://192.168.43.128/cbk/img/" + list_data.get(0).get("gambar"))
                            .crossFade()
                            .placeholder(R.mipmap.bocil)
                            .into(imgorang);
                    txtnama.setText(list_data.get(0).get("nama"));
                    txtnis.setText(list_data.get(0).get("nis"));
                    txtket.setText(list_data.get(0).get("ket"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cbk.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }

//        @Override
//        public void onBackPressed ()
//        {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//        }
}
