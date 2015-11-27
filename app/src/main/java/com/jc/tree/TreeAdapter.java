package com.jc.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lrh on 13/9/15.
 */
public class TreeAdapter extends BaseExpandableListAdapter {

    private List<String> group_list;
    private List<List<String>> item_list;

    private Context context;

    private LayoutInflater inflater;

    public TreeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        group_list = new ArrayList<String>();
        group_list.add("A");
        group_list.add("B");
        group_list.add("C");

        item_list = new ArrayList<List<String>>();
        item_list.add(group_list);
        item_list.add(group_list);
        item_list.add(group_list);
    }

    @Override
    public int getGroupCount() {
        return group_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return item_list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item_list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
//        if (convertView == null) {
//            convertView = (View) inflater.from(context).inflate(
//                    R.layout.deal_detail_group, null);
//            groupHolder = new GroupHolder();
//            groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
//            convertView.setTag(groupHolder);
//        } else {
//            groupHolder = (GroupHolder) convertView.getTag();
//        }
        groupHolder.txt.setText(group_list.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
//        if (convertView == null) {
//            convertView = (View) inflater.from(context).inflate(
//                    R.layout.deal_detail_item, null);
//            itemHolder = new ItemHolder();
//            itemHolder.txt = (TextView) convertView.findViewById(R.id.txt);
//            itemHolder.img = (ImageView) convertView.findViewById(R.id.img);
//            convertView.setTag(itemHolder);
//        } else {
//            itemHolder = (ItemHolder) convertView.getTag();
//        }
        itemHolder.txt.setText(item_list.get(groupPosition).get(
                childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        public TextView txt;
        public ImageView img;
    }

    class ItemHolder {
        public ImageView img;
        public TextView txt;
    }
}
