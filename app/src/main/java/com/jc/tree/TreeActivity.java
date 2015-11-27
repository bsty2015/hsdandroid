package com.jc.tree;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.jc.R;

/**
 * Created by lrh on 13/9/15.
 */
public class TreeActivity extends Activity {

    private ExpandableListView expandableListView;

    private ExpandableListView tree = null;
    private ExpandableListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_detail_main);
        //expandableListView = (ExpandableListView) findViewById(R.id.tree_list);
        TreeAdapter adapter = new TreeAdapter(this);
        expandableListView.setAdapter(adapter);
    }
}
