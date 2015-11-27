package com.jc.details;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.jc.base.CommonFragment;
import com.jc.ui.CustomProgress;
import com.jc.utils.DateSerializerUtil;
import com.jc.utils.GsonRequest;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lrh on 29/8/15.
 */
public class DealDetailFragment extends CommonFragment {

    private boolean isPrepared;
    protected View rootView;

    protected ExpandableListView contentListview;

    private String type;

    private View notdataView;

    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
            new DateSerializerUtil()).setDateFormat(DateFormat.LONG).setPrettyPrinting()
            .create();

    private static Map<String, String> urlMap = new HashMap<String, String>();

    static {
        urlMap.put("all", Configure.API_HOST + "/api/deal/list");
        urlMap.put("corpus", Configure.API_HOST + "/api/user/corpus/list");
        urlMap.put("profit", Configure.API_HOST + "/api/profit/list");
        urlMap.put("gift", Configure.API_HOST + "/api/cash/list");
        urlMap.put("cash", Configure.API_HOST + "/api/deal/paylist");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 避免fragment重绘UI
         */
        if (rootView == null) {
            rootView = inflater.inflate(com.jc.R.layout.deal_detail_list, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        contentListview = (ExpandableListView) rootView.findViewById(com.jc.R.id.dealDetaillist);
        isPrepared = true;
        isVisible = true;
        notdataView = rootView.findViewById(com.jc.R.id.notdata);
        loadData();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void hideNotdata() {
        if (notdataView != null) {
            notdataView.setVisibility(View.INVISIBLE);
        }
    }


    public void loadData() {
        String url = urlMap.get(type);
        final DealDetailAdapter adapter = new DealDetailAdapter(getActivity(), contentListview, type);
        final CustomProgress cp = CustomProgress.show(getActivity());
        GsonRequest request = new GsonRequest(url, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                cp.dismiss();
                if (response.isSucc()) {
                    Map<String, Object> rs = (Map<String, Object>) response.getData();
                    Object data = rs.get("details");
                    if (data != null) {
                        List<DealDetail> dealDetails = gson.fromJson(gson.toJson(data), new TypeToken<List<DealDetail>>() {
                        }.getType());
                        if (dealDetails != null && !dealDetails.isEmpty()) {
                            hideNotdata();
                            adapter.attachData(dealDetails);
                            contentListview.setAdapter(adapter);
                            contentListview.expandGroup(0);
                        }
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), response.getErrMsg(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        request.withRequestParams("userId", app.getUserId().toString());
        addRequest(request);
    }

    @Override
    protected String getFragmentReqTag() {
        return this.getClass().getCanonicalName() + type;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        loadData();
    }

    @Override
    public void setArguments(Bundle bundle) {//接收传入的数据
        type = bundle.getString("type");
    }

}
