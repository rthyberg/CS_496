package com.example.thymaster.five_views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thymaster on 2/19/17.
 */
public class GridViewActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GridView gridActivity;
    private ArrayAdapter<String> gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        // Find the GridView resource.
        gridActivity = (GridView) findViewById( R.id.gridview);

        // Create and populate a List of items.
        String[] items = new String[] { "Item_1","Item_2","Item_3","Item_4","Item_5"};
        ArrayList<String> itemList = new ArrayList<String>();
        itemList.addAll( Arrays.asList(items) );
        // Create ArrayAdapter using the items list.
        gridAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, itemList);
        // Set the ArrayAdapter as the ListView's adapter.
        gridActivity.setAdapter( gridAdapter );

    }
}