package com.example.thymaster.finalmobileclient;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.AuthCredential;
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




public class MainActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private FirebaseAuth mAuth;
    private OkHttpClient mOkHttpClient;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            // [END config_signin]

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this , this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();


            mOkHttpClient = new OkHttpClient();

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
            try {
                response = mOkHttpClient.newCall(request).execute();
                System.out.println(response.body().string());
                if (!response.isSuccessful()) System.out.println("Unexpected code " + response);
            } catch (IOException ie) {

            }
        }

        // create onclick event to change activities for each textView
        TextView listViewItem = (TextView) findViewById(R.id.Layout1);
        listViewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowData.class);
                startActivity(intent);
            }
        });

        TextView gridViewItem = (TextView) findViewById(R.id.Layout2);
        gridViewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateData.class);
                startActivity(intent);
            }
        });

        TextView horiViewItem = (TextView) findViewById(R.id.Layout3);
        horiViewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    String myuid = mAuth.getCurrentUser().getUid();
                    HttpUrl reqUrl = HttpUrl.parse("https://dotted-wind-155802.appspot.com/users/" + myuid);
                    Request request = new Request.Builder()
                            .url(reqUrl)
                            .delete()
                            .build();
                    Response response;
                    try {
                        response = mOkHttpClient.newCall(request).execute();
                        System.out.println(response.body().string());
                        if (!response.isSuccessful())
                            System.out.println("Unexpected code " + response);
                    } catch (IOException ie) {

                    }
                    mAuth.signOut();

                    // Google revoke access
                    Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                }
            }
        });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In)application/json will not
        // be available.

    }

}