package com.eclairiose.triviaapp.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.eclairiose.triviaapp.Controller.AppController;
import com.eclairiose.triviaapp.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    //String url = "http://192.168.43.174/txt";
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Questions> getData(final AnswerListAsyncResponse callback) {
        ArrayList<Questions> questionsArrayList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i <response.length() ; i++) {
                    try {
                        Log.e( "REPOo", "onResponse: "+response.getJSONArray( i ).get( 0 ) );
                        Questions questions = new Questions( response.getJSONArray( i ).get( 0 ).toString(), (Boolean) response.getJSONArray( i ).get( 1 ) );
                        questionsArrayList.add( questions );
                    }


                    catch (JSONException e) {
                        e.printStackTrace();
                        Log.e( "Sss", "onResponse: "+e  );
                    }

                }
                if(null!=callback)callback.processFinised(questionsArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "Repo", "onResponse: " + error );
            }
        } );

        //this Should be called inside the function body like in main we call it in the onCreate function
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionsArrayList;
    }
}
