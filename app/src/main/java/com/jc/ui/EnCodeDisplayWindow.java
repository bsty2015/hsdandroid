package com.jc.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.jc.image.LoadImage;
import com.jc.R;

/**
 * Created by lrh on 10/9/15.
 */
public class EnCodeDisplayWindow extends PopupWindow {

    private View contentView;

    protected ListView cardListview;

    private RelativeLayout closeButton;

    public EnCodeDisplayWindow(Activity context, int layoutId,LoadImage loadImage){
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(layoutId, null);
        NetworkImageView imageView = (NetworkImageView) contentView.findViewById(R.id.showImg);
        if(loadImage != null && imageView!= null){
            loadImage.load(imageView);
        }
        closeButton = (RelativeLayout) contentView.findViewById(R.id.closeButton);

        this.setContentView(contentView);

        //设置SelectPicPopupWindow弹出窗体的宽

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果

        this.setAnimationStyle(R.style.popupAnimation);

        //实例化一个ColorDrawable颜色为半透明

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.showAtLocation(context.findViewById(R.id.topLayout), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public static EnCodeDisplayWindow show(Activity context, int layoutId,LoadImage loadImage){
        return new EnCodeDisplayWindow(context,layoutId,loadImage);
    }
}
