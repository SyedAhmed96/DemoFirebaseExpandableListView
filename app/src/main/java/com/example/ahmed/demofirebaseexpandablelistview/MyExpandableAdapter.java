package com.example.ahmed.demofirebaseexpandablelistview;

/**
 * Created by Ahmed on 2/16/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class MyExpandableAdapter extends BaseExpandableListAdapter{
    private Context con;
    private List<String> headers;
    private HashMap<String,List<String>> headeritems;


    public MyExpandableAdapter(Context context, List<String> listheaders, HashMap<String,List<String>> headerchilds)
    {
        this.con=context;
        this.headers=listheaders;
        this.headeritems=headerchilds;
    }
    // get child of header
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.headeritems.get(this.headers.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    // return the view of child
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childname=(String)getChild(groupPosition,childPosition);
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)LayoutInflater.from(con);
            convertView=inflater.inflate(R.layout.headers_items,null);
        }
        TextView listchild=(TextView)convertView.findViewById(R.id.headeritem);
        listchild.setText(childname);
        return convertView;
    }
    // returns children count of header
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.headeritems.get(headers.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this.headers.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //returns the view of group
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headername=(String)getGroup(groupPosition);
        if(convertView==null)
        {
            LayoutInflater inflater=LayoutInflater.from(con);
            convertView=inflater.inflate(R.layout.listview_header,null);
        }
        TextView header=(TextView)convertView.findViewById(R.id.expandheader);
        header.setText(headername);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
}