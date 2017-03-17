package com.example.thymaster.finalmobileclient;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.HttpUrl;

/**
 * Created by thymaster on 2/19/17.
 */

public class ListViewActivity  extends BaseActivity {
    private ListView listActivity ;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        HttpUrl reqUrl = HttpUrl.parse("https://dotted-wind-155802.appspot.com");


        // Find the ListView resource.
        listActivity = (ListView) findViewById( R.id.list);

        // Create and populate a List of items.
        String[] items = new String[] { "Item_1","Item_2","Item_3","Item_4","Item_5"};
        ArrayList<String> itemList = new ArrayList<String>();
        itemList.addAll( Arrays.asList(items) );
        // Create ArrayAdapter using the item list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, itemList);
          // Set the ArrayAdapter as the ListView's adapter.
        listActivity.setAdapter( listAdapter );

    }
}
