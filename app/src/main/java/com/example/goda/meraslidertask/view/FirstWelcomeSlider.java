package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.goda.meraslidertask.models.slider.Slider;
import com.example.goda.meraslidertask.models.slider.SliderResults;
import com.example.goda.meraslidertask.utils.PreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstWelcomeSlider extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.caption_tv)
    TextView caption;
    @BindView(R.id.sliderLinearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.more_btn)
    Button more;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private Slider slider;
    private static final String API = "http://mera.live/api/codedecode/slider";
    private List<SliderResults> sliderResults = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_welcome_slider);
        ButterKnife.bind(this);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        slider = new Slider();

        PreferencesUtils.deleteEmail(this);

        String email = PreferencesUtils.getEmail(this);
        if (email != null && email != ""){
//            Toast.makeText(this,"already logged in", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(FirstWelcomeSlider.this, ClientData.class);
            startActivity(intent);
        }else {
//            Toast.makeText(this,"new login", Toast.LENGTH_LONG).show();
        }

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        more.setOnClickListener(this);
        getData();
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            Intent intent = new Intent(FirstWelcomeSlider.this, LoginActivity.class);
            startActivity(intent);
        }
        else if(view == registerButton){
            Intent intent = new Intent(FirstWelcomeSlider.this, RegisterationConditions.class);
            startActivity(intent);
        }else if(view == more) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sliderResults.get(0).getTargetLink()));
            startActivity(browserIntent);
        }
    }

    private void getData (){
        stringRequest = new StringRequest(Request.Method.GET, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(FirstWelcomeSlider.this, response, Toast.LENGTH_LONG).show();

              slider = gson.fromJson(response, Slider.class);

              if (slider != null){
                  sliderResults = slider.getResult();
                  setView(sliderResults);
              }else {
                  Toast.makeText(FirstWelcomeSlider.this, "null object", Toast.LENGTH_LONG).show();
              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Snackbar.make(linearLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getData();
                                    }
                                }).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        Snackbar.make(linearLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getData();
                                    }
                                }).show();
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");
                        Log.e("Error Status", status);
                        Log.e("Error Message", message);
                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                            Snackbar.make(linearLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getData();
                                        }
                                    }).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                            Toast.makeText(FirstWelcomeSlider.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                            Toast.makeText(FirstWelcomeSlider.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                            Snackbar.make(linearLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getData();
                                        }
                                    }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void setView (List<SliderResults> sliderResults){
        caption.setText(sliderResults.get(0).getSlideCaptionAr());
    }
}
