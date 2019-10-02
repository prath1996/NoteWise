package com.example.notewise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    private ExpandingList expandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this, listGroup, listItem);
        initListData();
        expandingList = findViewById(R.id.expanding_list_main);

        for (String groupHeader : listGroup) {
            ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
            ((TextView) item.findViewById(R.id.parent_text)).setText(groupHeader);

            int size = Objects.requireNonNull(listItem.get(groupHeader)).size();
            item.createSubItems(size);

            int count = 0;
            for (String subItem : Objects.requireNonNull(listItem.get(groupHeader))) {
                View subItemZero = item.getSubItemView(count);
                ((TextView) subItemZero.findViewById(R.id.child_text)).setText(subItem);
                count++;
            }
//            item.setIndicatorColorRes(R.color.blue);
//            item.setIndicatorIconRes(R.drawable.ic_icon);
        }

    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));

        String[] array;

        array = getResources().getStringArray(R.array.group1);
        List<String> list1 = new ArrayList<>(Arrays.asList(array));

        array = getResources().getStringArray(R.array.group2);
        List<String> list2 = new ArrayList<>(Arrays.asList(array));

        array = getResources().getStringArray(R.array.group3);
        List<String> list3 = new ArrayList<>(Arrays.asList(array));

        listItem.put(listGroup.get(0), list1);
        listItem.put(listGroup.get(1), list2);
        listItem.put(listGroup.get(2), list3);

        adapter.notifyDataSetChanged();
    }


}











