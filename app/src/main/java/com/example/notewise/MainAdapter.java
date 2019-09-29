package com.example.notewise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

class MainAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listGroup;
    private HashMap<String, List<String>> listItem;

    MainAdapter(Context context,
                List<String> listGroup,
                HashMap<String, List<String>> listItem) {
        this.context = context;
        this.listGroup = listGroup;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return listItem.get(listGroup.get(groupPos)).size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return listGroup.get(groupPos);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        return listItem.get(listGroup.get(groupPos)).get(childPos);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        String group = listGroup.get(groupPos);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_element,null);
        }
        TextView textView = convertView.findViewById(R.id.parent_text);
        textView.setText(group);

        return convertView;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        String child = listItem.get(listGroup.get(groupPos)).get(childPos);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child_element, null);
        }

        TextView textView = convertView.findViewById(R.id.child_text);
        textView.setText(child);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
