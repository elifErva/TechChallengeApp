package com.evra.techchallengemarket;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private Context context;
    private RequestQueue requestQueue;

    public MySingleton(final Context context) {
        this.context = context;
        requestQueue = getRequestQueue();

    }

    //yeni bir istek kuyruğu oluşturuyoruz ve bu kuyruk constructor içerisinden çağırılıyor
    private RequestQueue getRequestQueue() {
        return Volley.newRequestQueue(context);
    }
    public static synchronized MySingleton getInstance(Context context){
        return new MySingleton(context);
    }
    //indirme işlemi bu kod ile yapılır
    public <T> void addRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
