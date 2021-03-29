package com.sense.bitcoinapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn;
    TextView koinim,paribu,btcturk,koineks,txtvolume,txtLow,txtHighBtcturk;
    TextView txtHighParibu,txtLowParibu,txtvolumeParibu;
    TextView txtHighKoinim,txtLowKoinim,txtvolumeKoinim;
    TextView txtHighKoineks,txtLowKoineks,txtvolumeKoineks;

    String url1 = "https://www.btcturk.com/api/ticker";

    ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn_get_information);

        paribu = (TextView)findViewById(R.id.paribu);
        koineks = (TextView)findViewById(R.id.koineks);
        koinim = (TextView)findViewById(R.id.koinim);
        btcturk = (TextView)findViewById(R.id.btcturk);

        txtvolume = (TextView)findViewById(R.id.txtvolume);
        txtLow = (TextView)findViewById(R.id.txtLow);
        txtHighBtcturk = (TextView)findViewById(R.id.txtHighBtcturk);

        txtHighParibu = (TextView)findViewById(R.id.txtHighParibu);
        txtLowParibu = (TextView)findViewById(R.id.txtLowParibu);
        txtvolumeParibu = (TextView)findViewById(R.id.txtvolumeParibu);

        txtHighKoineks = (TextView)findViewById(R.id.txtHighKoineks);
        txtLowKoineks = (TextView)findViewById(R.id.txtLowKoineks);
        txtvolumeKoineks = (TextView)findViewById(R.id.txtvolumeKoineks);

        txtLowKoinim = (TextView)findViewById(R.id.txtLowKoinim);
        txtHighKoinim = (TextView)findViewById(R.id.txtHighKoinim);
        txtvolumeKoinim = (TextView)findViewById(R.id.txtvolumeKoinim);

        urls.add("https://koinim.com/ticker/");
        urls.add("https://koineks.com/ticker");
        urls.add("https://www.paribu.com/ticker");

        btn.setOnClickListener(this);

       // onClick(null);

    }

    @Override

    public void onClick(final View v){

        getBtcTurk();

        if (v.getId() == R.id.btn_get_information){

            for(final String url :urls){
                JsonObjectRequest jsonObjectRequest = new
                        JsonObjectRequest(Request.Method.GET, url, null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if(url.contains("koineks") ){
                                    try {

                                        JSONObject BTC = response.getJSONObject("BTC");

                                        String current = BTC.getString("current");
                                        String high =BTC.getString("high");
                                        String low = BTC.getString("low");
                                        String volume = BTC.getString("volume");

                                        koineks.setText(" "+ current + " " + "₺");
                                        txtHighKoineks.setText("En Yüksek Fiyat\n" + high);
                                        txtLowKoineks.setText("En Düşük Fiyat\n" + low);
                                        txtvolumeKoineks.setText("Hacim\n" + volume);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                else if(url.contains("koinim")){
                                    try {
                                        koinim.setText(" " +
                                                response.getString("sell")+ " " + "₺" );

                                        txtHighKoinim.setText("En Yüksek Fiyat\n" +
                                        response.getString("high"));

                                        txtLowKoinim.setText("En Düşük Fiyat\n" +
                                        response.getString("low"));

                                        txtvolumeKoinim.setText("Hacim\n" +
                                        response.getString("volume"));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                else if( url.contains("paribu")){

                                    try {

                                        JSONObject BTC_TL = response.getJSONObject("BTC_TL");
                                        String last = BTC_TL.getString("last");
                                        String high24hr = BTC_TL.getString("high24hr");
                                        String low24hr = BTC_TL.getString("low24hr");
                                        String volume = BTC_TL.getString("volume");

                                        paribu.setText(" " + last + " " + "₺");
                                        txtHighParibu.setText("En Yüksek Fiyat\n" + high24hr);
                                        txtLowParibu.setText("En Düşük Fiyat\n" + low24hr);
                                        txtvolumeParibu.setText("Hacim\n"+ volume);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText((getApplicationContext()),"Error",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }

                        });

                MySingleton.getmInstance(getApplicationContext()).
                        addToRequestQue(jsonObjectRequest);
            }
        }

    }
    private void getBtcTurk(){

         StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);

                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject btc = (JSONObject) jsonarray.get(0);
                                String last = btc.getString("last");
                                String volume = btc.getString("volume");
                                String high = btc.getString("high");
                                String  low = btc.getString("low");
                                btcturk.setText(" " + last + " " + "₺");
                                txtvolume.setText("Hacim\n "  + volume);
                                txtHighBtcturk.setText("En Yüksek Fiyat\n" + high);
                                txtLow.setText("En Düşük Fiyat\n" + low);
                        }

                        } catch (JSONException e1) {
                            e1.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText((getApplicationContext()),"Error",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }

                });

        MySingleton.getmInstance(getApplicationContext()).
                addToRequestQue(stringRequest);
    }

    }
