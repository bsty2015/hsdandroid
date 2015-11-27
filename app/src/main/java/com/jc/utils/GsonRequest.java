package com.jc.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jc.base.CommonAdapter;
import com.jc.base.CustomApplication;
import com.jc.base.ResultData;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lrh on 23/7/15.
 */
public class GsonRequest extends Request<ResultData> {

    private static final String ERR_CODE = "err_code";

    private static final String ERR_MSG = "err_msg";

    private final Listener<ResultData>  mListener;

    private Gson mGson;

    private Map<String, String> params;

    public GsonRequest(int method, String url,Listener<ResultData> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mListener = listener;
        setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 1, 1.0f));
    }

    public GsonRequest(String url,Listener<ResultData> listener,
                       ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
    }

    public GsonRequest(String url,Listener<ResultData> listener) {
        this(Method.POST, url, listener, new DefualtErrorListener(listener));
    }

    public GsonRequest(String url, final CommonAdapter adapter) {
        this(Method.POST, url, new Response.Listener<ResultData>(){

            @Override
            public void onResponse(final ResultData response) {
                adapter.render(response);
            }
        }, new DefualtErrorListener(adapter));
    }

    public GsonRequest withRequestParams(Map<String, String> params){
        if(this.params == null){
            this.params = new HashMap<>();
        }
        this.params.putAll(params);
        return  this;
    }

    public GsonRequest withRequestParams(String key,String value){
        if(this.params == null){
            this.params = new HashMap<>();
        }

        this.params.put(key,value);
        return  this;
    }

    @Override
    protected Response<ResultData> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, ResultData.class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> reqParam = super.getParams() == null?new HashMap<String, String>():super.getParams();
        if(params != null && !params.isEmpty()){
            reqParam =  params;
        }
        reqParam.put("pageSize","100000");
        reqParam.put("accessToken", CustomApplication.accessToken);
        reqParam.putAll(CustomApplication.info);
        return reqParam;
    }

    @Override
    protected void deliverResponse(ResultData response) {
        mListener.onResponse(response);
    }

    public static  class DefualtErrorListener implements ErrorListener{

        private  Listener<ResultData>  mListener;
        private CommonAdapter adapter;

        public DefualtErrorListener(Listener<ResultData>  mListener){
            this.mListener = mListener;

        }

        public DefualtErrorListener(CommonAdapter adapter){
            this.adapter = adapter;

        }
        @Override
        public void onErrorResponse(VolleyError error) {
            ResultData data = new ResultData();
            data.setErrCode("-1");
            data.setErrMsg(error.getMessage());
            if(mListener != null ){
                mListener.onResponse(data);
            }

            if(adapter != null ){
                adapter.render(data);
            }

        }
    }

}
