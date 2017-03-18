package com.example.thymaster.finalmobileclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by thymaster on 3/17/17.
 */

public class ShowData extends BaseActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private FirebaseAuth mAuth;
    private OkHttpClient mOkHttpClient;
    private ListView listActivity;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_list_view);

        String name = "null";
        String beer = "null";
        int rating = 0;
        String notes = "null";

        mAuth = FirebaseAuth.getInstance();
        mOkHttpClient = new OkHttpClient();

        if(mAuth.getCurrentUser()!= null) {
            String myuid = mAuth.getCurrentUser().getUid();
            String json;
            HttpUrl reqUrl = HttpUrl.parse("https://dotted-wind-155802.appspot.com/users/" + myuid);
            Request request = new Request.Builder()
                    .url(reqUrl)
                    .build();
            Response response;
            try {
                response = mOkHttpClient.newCall(request).execute();
                String r = response.body().string();
                try {
                    JSONObject j = new JSONObject(r);
                    name = j.getString("name");
                    beer = j.getString("beer");
                    rating = j.getInt("rating");
                    notes = j.getString("notes");
                    //  if(name == null) name = "null";
                    //  if(beer == null) beer = "null";
                    //  if(notes == null) notes = "null";
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (!response.isSuccessful()) System.out.println("Unexpected code " + response);
            } catch (IOException ie) {

            }

            // Find the ListView resource.
            listActivity = (ListView) findViewById(R.id.list);
            String number = String.valueOf(rating);
            String[] items = new String[]{name, beer, number, notes};

            // Create and populate a List of items.
            // String[] items = new String[]{name, beer, number, notes};
            ArrayList<String> itemList = new ArrayList<String>();
            itemList.addAll(Arrays.asList(items));
            // Create ArrayAdapter using the item list.
            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, itemList);
            // Set the ArrayAdapter as the ListView's adapter.
            listActivity.setAdapter(listAdapter);
        } else {
            // Find the ListView resource.
            listActivity = (ListView) findViewById(R.id.list);
            String number = String.valueOf(rating);
            String[] items = new String[]{"DefaultName", "DefaultBeer", "0", "DefaultNotes"};

            // Create and populate a List of items.
            // String[] items = new String[]{name, beer, number, notes};
            ArrayList<String> itemList = new ArrayList<String>();
            itemList.addAll(Arrays.asList(items));
            // Create ArrayAdapter using the item list.
            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, itemList);
            // Set the ArrayAdapter as the ListView's adapter.
            listActivity.setAdapter(listAdapter);
        }
    }
}