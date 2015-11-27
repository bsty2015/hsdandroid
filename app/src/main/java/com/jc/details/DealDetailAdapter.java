package com.jc.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jc.utils.DateFormatUtil;


/**
 * Created by lrh on 13/9/15.
 */
public class DealDetailAdapter extends BaseExpandableListAdapter {

    private static  List<String> types = Arrays.asList("corpus","profit","gift");

    private List<String> group_list;
    private List<List<DealDetail>> item_list;


    private Context context;

    private String type;

    private LayoutInflater inflater;


    public DealDetailAdapter(Context context, final ExpandableListView contentListview,String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        group_list = new ArrayList<String>();
        item_list = new ArrayList<List<DealDetail>>();
        contentListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                    for(int i = 0;i<group_list.size();i++){
                        if(groupPosition != i){
                            contentListview.collapseGroup(i);
                        }
                    }
            }
        });
        this.type = type;

    }

    public void attachData(List<DealDetail> details){
        for(DealDetail detail:details){
            if(types.contains(type)){
                detail.setStatus("1");
            }
            String groupKey = DateFormatUtil.date2String(detail.getDate(), DateFormatUtil.DATE_FORMAT);
            int index = group_list.indexOf(groupKey);
            if(index ==  -1){
                index = group_list.size();
                List<DealDetail> tmp = new ArrayList<DealDetail>();
                tmp.add(detail);
                item_list.add(index,tmp);
                group_list.add(groupKey);

            }else{
                item_list.get(index).add(detail);
            }
        }
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
        if (convertView == null) {
            convertView = (View) inflater.from(context).inflate(
                    com.jc.R.layout.deal_detail_group, null);
            groupHolder = new GroupHolder();
            groupHolder.detailGroupText = (TextView) convertView.findViewById(com.jc.R.id.detailGroupText);
            groupHolder.unSelectedIcon = (ImageView) convertView.findViewById(com.jc.R.id.unSelectedIcon);
            groupHolder.selectedIcon = (ImageView) convertView.findViewById(com.jc.R.id.selectedIcon);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.detailGroupText.setText(group_list.get(groupPosition));
        if(isExpanded){
            groupHolder.unSelectedIcon.setVisibility(View.GONE);
            groupHolder.selectedIcon.setVisibility(View.VISIBLE);
        }else {
            groupHolder.unSelectedIcon.setVisibility(View.VISIBLE);
            groupHolder.selectedIcon.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = (View) inflater.from(context).inflate(
                    com.jc.R.layout.deal_detail_item, null);
            itemHolder = new ItemHolder();
            itemHolder.detailItemText = (TextView) convertView.findViewById(com.jc.R.id.detailItemText);
            itemHolder.payInfo = (TextView) convertView.findViewById(com.jc.R.id.payInfo);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        DealDetail detail = item_list.get(groupPosition).get(childPosition);
        itemHolder.detailItemText.setText(detail.getDesc());
        itemHolder.payInfo.setText(detail.getPayInfo());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        public TextView detailGroupText;
        public ImageView unSelectedIcon;
        public ImageView selectedIcon;
    }

    class ItemHolder {
        public TextView payInfo;
        public TextView detailItemText;
    }
}
