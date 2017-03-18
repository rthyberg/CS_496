package com.example.thymaster.finalmobileclient;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by thymaster on 3/17/17.
 */

public class UpdateData extends BaseActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private FirebaseAuth mAuth;
    private OkHttpClient mOkHttpClient;
    private ListView listActivity;
    private ArrayAdapter<String> listAdapter;
    private Button updateSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_update);
        mAuth = FirebaseAuth.getInstance();
        mOkHttpClient = new OkHttpClient();
        updateSubmitButton = (Button) findViewById(R.id.sql_add_row_button);
        updateSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser() != null) {
                    String name = ((EditText) findViewById(R.id.textNameInput)).getText().toString();
                    String beer = ((EditText) findViewById(R.id.textBeerInput)).getText().toString();
                    String rating = ((EditText) findViewById(R.id.textRatingInput)).getText().toString();
                    String notes = ((EditText) findViewById(R.id.textNotesInput)).getText().toString();

                    String myuid = mAuth.getCurrentUser().getUid();
                    String json = "{ \"userToken\":  \"" + myuid + "\", \"name\":\"" + name + "\", \"beer\":\"" + beer + "\", \"rating\":" + rating + ", \"notes\":\"" + notes + "\"}";

                    RequestBody body = RequestBody.create(JSON, json);
                    HttpUrl reqUrl = HttpUrl.parse("https://dotted-wind-155802.appspot.com/users/" + myuid);
                    Request request = new Request.Builder()
                            .url(reqUrl)
                            .put(body)
                            .build();
                    try {
                        mOkHttpClient.newCall(request).execute();
                    } catch (IOException ie) {

                    }
                }
            }

        });
    }
}