package com.jc.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by zy on 15/7/20.
 */
public class HttpUtils {
    private Context context;
    private RequestQueue requestQueue;
   private void getData(){
       //联网请求数据

       RequestQueue mQueue = Volley.newRequestQueue(context);
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               Log.d("TAG", response.toString());
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.e("TAG", error.getMessage(), error);
           }
       });
       mQueue.add(jsonObjectRequest);

    }
}
