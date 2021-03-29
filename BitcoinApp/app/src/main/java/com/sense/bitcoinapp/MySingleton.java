package com.sense.bitcoinapp;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sense on 28.11.2017.
 */

public class MySingleton {
    private static Context ctx;
    private static MySingleton mInstance;
    private RequestQueue requestQueue;

    private MySingleton ( Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }
    private RequestQueue getRequestQueue (){
        return Volley.newRequestQueue(ctx);
    }

    public static synchronized MySingleton getmInstance (Context context){
        return new MySingleton(context);
    }
    public <T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);
    }
}
