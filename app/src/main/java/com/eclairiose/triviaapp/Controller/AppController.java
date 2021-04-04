package com.eclairiose.triviaapp.Controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eclairiose.triviaapp.MainActivity;

public class AppController extends Application {
    public static AppController instance;
    private RequestQueue requestQueue;

//    private AppController()
//    {
//        // Constructor hidden because this is a singleton
//    }

    public static synchronized AppController getInstance() {
         if(instance==null){
             instance = new AppController();
             }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add( req );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
