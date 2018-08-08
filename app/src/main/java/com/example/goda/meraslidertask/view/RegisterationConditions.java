package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.models.terms.Terms;
import com.example.goda.meraslidertask.models.terms.TermsResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterationConditions extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.conditionsCheckBox)
    CheckBox checkBox;
    @BindView(R.id.conditionsNextbutton)
    Button next;
    @BindView(R.id.mainDataActivityButton)
    Button maindataButton;
    @BindView(R.id.clientDataButton)
    Button clientdataButton;
    @BindView(R.id.theEndButton)
    Button theendButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.conditions_tv)
    TextView termsTv;
    private boolean checked;
    private static final String API = "http://mera.live/api/user/get_user_terms_conditions/";
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private Gson gson;
    private Terms terms;
    private List<TermsResult> termsResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_conditions);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.newUserTitle);

        checkBox.setOnClickListener(this);
        next.setOnClickListener(this);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        terms= new Terms();
        getData();

    }

    @Override
    public void onClick(View view) {
        if (view == checkBox) {
            if (checkBox.isChecked()) {
                checked = true;
            } else {
                checked = false;
            }
        }else if (view == next){
            if(checked) {
                Intent intent = new Intent(RegisterationConditions.this, ServiceProviderEntry.class);
                intent.putExtra("terms",1);
                startActivity(intent);
            }else {
                Toast.makeText(this,"يجب الموافقه على شروط التسجيل اولا", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getData(){
        stringRequest = new StringRequest(Request.Method.GET, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             terms = gson.fromJson(response, Terms.class);
             if(terms != null){
                 termsResults = terms.getResult();
                 setView(termsResults);
             }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void setView(List<TermsResult> termsResults) {
        termsTv.setText(termsResults.get(0).getTermsConditionsAr());
    }
}
