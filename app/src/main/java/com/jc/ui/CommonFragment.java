package com.jc.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.jc.R;

/**
 * Created by lrh on 9/8/15.
 */
public class CommonFragment extends Fragment {

    protected View rootView;



    protected void setHeadTitleName(String name){
        TextView tv = (TextView) rootView.findViewById(R.id.titleText);
        tv.setText(name);
    }

    protected void hideBackButton(){
        rootView.findViewById(R.id.backIcon).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.backText).setVisibility(View.INVISIBLE);
    }
}
