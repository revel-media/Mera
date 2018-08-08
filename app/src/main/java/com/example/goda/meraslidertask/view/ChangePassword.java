package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.PendingIntent.getActivity;

public class ChangePassword extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button changePassword_btn;
    @BindView(R.id.change_email_et)
    EditText email;
    @BindView(R.id.change_password_et)
    EditText changedPassword;
    @BindView(R.id.login_relativelayout)
    RelativeLayout relativeLayout;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final static String API ="http://mera.live/api/user/change_password/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);

    }

    public void change(View view) {
//        Toast.makeText(ChangePassword.this,"toast", Toast.LENGTH_LONG).show();
        sendData();
    }

    private void sendData(){

        stringRequest = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Snackbar snackbar = Snackbar.make(relativeLayout, "تم تغيير كلمة السر", Snackbar.LENGTH_INDEFINITE).setActionTextColor(ContextCompat.getColor(ChangePassword.this, R.color.colorPrimary));

                snackbar.getView().setBackgroundColor(ContextCompat.getColor(ChangePassword.this, R.color.colorAccent));
                snackbar.setAction(R.string.login, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePassword.this,error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("email",email.getText().toString());
                params.put("password", changedPassword.getText().toString());


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
