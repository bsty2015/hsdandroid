package com.jc.capital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jc.base.CommonFragment;
import com.jc.base.Configure;
import com.jc.ui.FreshenList;
import com.jc.utils.GsonRequest;


/**
 * Created by lrh on 29/8/15.
 */
public class CapitalFragment extends CommonFragment {


    private String reqUrl = Configure.API_HOST+"/api/user/invest/product";

    protected View rootView;

    protected FreshenList contentListview;

    private String status;


    private Handler handler = new Handler();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(com.jc.R.layout.commonlist_layout, container, false);
        contentListview = (FreshenList) rootView.findViewById(com.jc.R.id.listView);
        getData();
        contentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Capital capital = (Capital) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), InvestProDetailActivity.class);
                intent.putExtra("id", capital.getId().toString());
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        CapitalAdapter adapter = new CapitalAdapter(getActivity(),new CapitalHolder(status),contentListview);
        adapter.withHandler(handler);
        GsonRequest request = new GsonRequest(reqUrl,adapter);
        request.withRequestParams("status",status).withRequestParams("userId",app.getUserId().toString()).withRequestParams("pageSize","100000");
        addRequest(request);
    }

    @Override
    public void setArguments(Bundle bundle) {//接收传入的数据
        status=bundle.getString("name");
    }




    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                CapitalAdapter adapter = new CapitalAdapter(getActivity(),new CapitalHolder(status),contentListview);
                adapter.withHandler(handler);
                GsonRequest request = new GsonRequest(reqUrl,adapter);
                request.withRequestParams("status",status).withRequestParams("userId",app.getUserId().toString()).withRequestParams("pageSize","100000");
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }

    @Override
    protected void lazyLoad() {

    }
}
