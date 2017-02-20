package com.example.thymaster.five_views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thymaster on 2/19/17.
 */

public class ListViewActivity  extends AppCompatActivity {
    private ListView listActivity ;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

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
