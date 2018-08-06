package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.models.login.Login;
import com.example.goda.meraslidertask.models.login.LoginResults;
import com.example.goda.meraslidertask.utils.PreferencesUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    @BindView(R.id.login_relativelayout)
    RelativeLayout relativeLayout;


    private RequestQueue requestQueue ;
    private StringRequest stringRequest;
    private String BACK_END_LOGIN = "http://mera.live/api/user/login";
    private Login login ;
    private Gson gson;
    private LoginResults loginResults;
    private String account_type;
    private String json;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        requestQueue= Volley.newRequestQueue(this);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);


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

    private void sendData() {

        stringRequest = new StringRequest(Request.Method.POST, BACK_END_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();

                login = gson.fromJson(response, Login.class);
                if (login != null){
                    userId = login.getResult().getUserID();
                    PreferencesUtils.saveId(userId, LoginActivity.this);
                    loginResults = login.getResult();
                    account_type = loginResults.getTypeID();
                    if (account_type.matches("1")){
                        Intent intent = new Intent(LoginActivity.this, ClientHome.class);
                        startActivity(intent);
                    }else if (account_type.matches("2")){
                        Intent intent = new Intent(LoginActivity.this, ServiceProviderHome.class);
                        startActivity(intent);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                NetworkResponse networkResponse = error.networkResponse;

//                networkResponse = error.networkResponse;
//                if (networkResponse != null && networkResponse.data != null) {
//                    if (networkResponse. == 400) {
//                        json = new String(networkResponse.data);
//                        json = trimMessage(json, "message");
//                        if (json != null) displayMessage(json);
//
//                    }
                    //Additional cases

                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            errorMessage = "Request timeout";
                            Snackbar.make(relativeLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendData();
                                        }
                                    }).show();
                            // Toast.makeText(GroupSettings.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                            Snackbar.make(relativeLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendData();
                                        }
                                    }).show();
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.getString(result).matches("0")) {
                                Toast.makeText(LoginActivity.this, "wrong email or password", Toast.LENGTH_LONG).show();
                            } else {
                                String status = response.getString("status");
                                String message = response.getString("message");
                                Log.e("Error Status", status);
                                Log.e("Error Message", message);
                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                    Snackbar.make(relativeLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                            .setAction(R.string.retry, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    sendData();
                                                }
                                            }).show();
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message + " Please login again";
                                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message + " Check your inputs";
                                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message + " Something is getting wrong";
                                    Snackbar.make(relativeLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                            .setAction(R.string.retry, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    sendData();
                                                }
                                            }).show();
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //Additional cases
                    Log.i("Error", errorMessage);
                    error.printStackTrace();
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

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        Toast.makeText(LoginActivity.this, toastString, Toast.LENGTH_LONG).show();
    }

}
