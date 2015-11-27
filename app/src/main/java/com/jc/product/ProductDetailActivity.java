package com.jc.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WebViewActivity;
import com.jc.utils.GsonRequest;

/**
 * Created by lrh on 6/8/15.
 */
public class ProductDetailActivity extends HeadMenuActiviyt {

    private String reqUrl = Configure.API_HOST+"/api/product/detail";

    private String assetDetailUrl = Configure.H5_HOST+"/product/assetdetail/";

    Integer productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_product_information);
        initView();
        setHeadTitleName("产品详情");
        Intent intent = getIntent();
        productId = intent.getIntExtra("id",0);

        findViewById(R.id.knowMoreProDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this,WebViewActivity.class);
                intent.putExtra("title","资产详情");
                intent.putExtra("url",assetDetailUrl+productId);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProductDetailAdapter adapter = new ProductDetailAdapter(this,new ProductDetailHolder(),null);
        GsonRequest request = new GsonRequest(reqUrl,adapter);
        request.withRequestParams("id",productId.toString());
        addRequest(request);
    }
}
