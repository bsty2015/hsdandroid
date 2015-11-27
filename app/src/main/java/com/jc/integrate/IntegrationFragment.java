package com.jc.integrate;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.base.AddRequestQueueInterface;
import com.jc.base.CommonFragment;
import com.jc.base.Configure;
import com.jc.ui.FreshenList;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/9/2.
 */
public class IntegrationFragment extends CommonFragment {


    private String reqUrl = Configure.API_HOST + "/api/credit/list";

    protected View rootView;

    protected FreshenList contentListview;

    private String status;

    private boolean isPrepared;
    private AddRequestQueueInterface queue;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isPrepared = true;
        isVisible = true;
        /**
         * 避免fragment重绘UI
         */
        if (rootView == null) {
            rootView = inflater.inflate(com.jc.R.layout.commonlist_layout, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        contentListview = (FreshenList) rootView.findViewById(com.jc.R.id.listView);
//        lazyLoad();
        loadData();
        getData();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    protected String getFragmentReqTag() {
        return this.getClass().getCanonicalName() + status;
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
        status = bundle.getString("name");
    }

    private void loadData() {
        IntegratioinAdapter adapter = new IntegratioinAdapter(getActivity(), new IntegrationiHolder(), contentListview);
        GsonRequest request = new GsonRequest(reqUrl, adapter);
        request.withRequestParams("isExchange", status).withRequestParams("userId", app.getUserId().toString());
        addRequest(request);
    }


    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
//                IntegratioinAdapter adapter = new IntegratioinAdapter(getActivity(),new IntegrationiHolder(),contentListview);
//                GsonRequest request = new GsonRequest(reqUrl,adapter);
//                request.withRequestParams("isExchange",status).withRequestParams("userId",app.getUserId().toString());
//                addRequest(request);
                loadData();
                contentListview.onRefreshComplete();
            }
        });

    }


}
