package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.models.login.Login;
import com.example.goda.meraslidertask.models.login.LoginResults;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_et)
    EditText email_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.register_btn)
    Button register_btn;
    @BindView(R.id.forgot_password)
    TextView forgot_password;


    private RequestQueue requestQueue ;
    private StringRequest stringRequest;
    private String BACK_END_LOGIN = "http://mera.live/api/user/login";
    private Login login ;
    private Gson gson;
    private LoginResults loginResults;
//    private GoogleApiClient googleApiClient;
//    private static final int GoogleLoginRequest = 777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        requestQueue= Volley.newRequestQueue(this);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

//
//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
//                .build();

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        login = new Login();

    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            sendData();
        }else if (view ==register_btn){
            Intent intent = new Intent(LoginActivity.this, RegisterationConditions.class);
            startActivity(intent);

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GoogleLoginRequest){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()){
//            goMainScreen();
//        }else {
//            Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void goMainScreen() {
//        Intent intent = new Intent(LoginActivity.this,GoogleSignInMainScreen.class);
//        startActivity(intent);
//    }

    private void sendData() {

        stringRequest = new StringRequest(Request.Method.POST, BACK_END_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                login = gson.fromJson(response, Login.class);
                if (login != null){
                    loginResults = login.getResult();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", email_et.getText().toString());
                params.put("password", password_et.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
