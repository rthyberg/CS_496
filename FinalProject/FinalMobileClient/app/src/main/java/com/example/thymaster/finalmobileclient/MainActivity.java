package com.example.thymaster.finalmobileclient;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class MainActivity extends BaseActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private FirebaseAuth mAuth;
    private OkHttpClient mOkHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        mAuth = FirebaseAuth.getInstance();
        mOkHttpClient = new OkHttpClient();
        setContentView(R.layout.activity_main);
        String myuid = mAuth.getCurrentUser().getUid();
        String json = "{ \"userToken\": \"" + myuid + "\"}";
                   //:w " \"name\": \"N/A\", }";
        HttpUrl reqUrl = HttpUrl.parse("https://dotted-wind-155802.appspot.com/users");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(reqUrl)
                .post(body)
                .build();
        Response response;
        try {response = mOkHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
            if (!response.isSuccessful()) System.out.println("Unexpected code " + response);
        } catch (IOException ie) {

        }

/*
        // create onclick event to change activities for each textView
        TextView listViewItem = (TextView) findViewById(R.id.Layout1);
        listViewItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        TextView gridViewItem = (TextView) findViewById(R.id.Layout2);
        gridViewItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                startActivity(intent);
            }
        });
        TextView horiViewItem= (TextView) findViewById(R.id.Layout3);
        horiViewItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HorizontalScrollActivity.class);
                startActivity(intent);
            }
        });
        TextView relaViewItem = (TextView) findViewById(R.id.Layout4);
        relaViewItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RelativeViewActivity.class);
                startActivity(intent);
            }
        });
    */

    }

}