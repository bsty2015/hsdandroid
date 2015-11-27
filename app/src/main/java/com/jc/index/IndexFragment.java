package com.jc.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jc.base.Configure;
import com.jc.base.CommonFragment;
import com.jc.ui.FreshenList;
import com.jc.ui.WebViewActivity;
import com.jc.utils.GsonRequest;

/**
 * Created by lrh on 9/8/15.
 */
public class IndexFragment extends CommonFragment {

    protected FreshenList contentListview;

    private GsonRequest request;

    private String reqUrl = Configure.API_HOST + "/api/banner/list";

    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isPrepared = true;
        isVisible = true;
        /**
         * 避免fragment重绘UI
         */
        if (rootView == null) {
            rootView = inflater.inflate(com.jc.R.layout.homepage, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        setHeadTitleName("集财");
        hideBackButton();

        contentListview = (FreshenList) rootView.findViewById(com.jc.R.id.listView);
        getIndexModule();
//        lazyLoad();
        getData();
        contentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexModule indexModule = (IndexModule) parent.getItemAtPosition(position);
                String link = indexModule.getLink();
                if ("#".equals(link)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", indexModule.getName());
                intent.putExtra("url", indexModule.getLink());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //getIndexModule();
    }

    private void getIndexModule() {

        IndexAdapter adapter = new IndexAdapter(getActivity(), new IndexHolder(), contentListview);
        request = new GsonRequest(reqUrl, adapter);
        addRequest(request);

    }


    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getIndexModule();
                contentListview.onRefreshComplete();
            }
        });

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getIndexModule();
    }
}
