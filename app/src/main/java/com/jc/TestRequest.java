package com.jc;




import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by zy on 15/7/20.
 */
public  class  TestRequest extends Request<Object> {

    private Priority mPriority = Priority.NORMAL;
    public TestRequest(Priority priority) {
        super(Request.Method.GET, "", null);
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    @Override
    protected void deliverResponse(Object response) {


    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        return null;
    }
}
