package com.jc.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.base.CommonFragment;
import com.jc.ui.FreshenList;
import com.jc.utils.GsonRequest;

import java.util.List;

/**
 * Created by lrh on 9/8/15.
 */
public class ProductFragment extends CommonFragment {

    private Handler handler = new Handler();

    private GsonRequest request;

    protected FreshenList contentListview;

    private String reqUrl = Configure.API_HOST + "/api/product/list";

    private List<String> lists;
    private ProductAdapter adapter;
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
            rootView = inflater.inflate(R.layout.homepage, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        setHeadTitleName("理财产品");
        hideBackButton();
        contentListview = (FreshenList) rootView.findViewById(R.id.listView);
//        lazyLoad();
        initData();
        getData();

        contentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("id", product.getId());
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    public void initData() {
        ProductAdapter adapter = new ProductAdapter(getActivity(), new ProductHolder(), contentListview);
        request = new GsonRequest(reqUrl, adapter);
        addRequest(request);
    }

    public void getData() {

        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                ProductAdapter adapter = new ProductAdapter(getActivity(), new ProductHolder(), contentListview);
                request = new GsonRequest(reqUrl, adapter);
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
    }
}
