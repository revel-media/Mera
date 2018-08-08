package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
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
import com.example.goda.meraslidertask.models.register.Register;
import com.example.goda.meraslidertask.utils.PreferencesUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzw;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationMainDataEntry extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.name_tv)
    EditText name_et;
    @BindView(R.id.phone_tv)
    EditText phone_et;
    @BindView(R.id.email_tv)
    EditText email_et;
    @BindView(R.id.pass_tv)
    EditText password_et;
    @BindView(R.id.confirmpass_tv)
    EditText confirmPassword_et;
    @BindView(R.id.client_radiobtn)
    RadioButton clientRadioButton;
    @BindView(R.id.serviceprovider_radiobtn)
    RadioButton serviceProviderRadioButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submit_data)
    Button next;
    @BindView(R.id.dataEntryScrollview)
    ScrollView scrollView;
    private String accountType;
    private String BACK_END_REGISTERATION = "http://mera.live/api/user/register_user";
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private Register register;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String deviceToken;
    private String terms;
    private int UserId;
    private String refreshedTokenid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main_data_entry);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            terms = extras.get("terms").toString();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.newUserTitle);

        next.setOnClickListener(this);
        FirebaseApp.initializeApp(this);
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this, deviceToken, Toast.LENGTH_LONG).show();

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        register = new Register();

    }

    private boolean validateName(String name) {
        if (name != null && name.length() >= 3) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePhoneNumber(String phone) {
        if (phone != null && phone.length() == 11) {
            this.phone = phone;
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePassword(String password, String confirmPassword) {
        if (password == null || password == "") {
            return false;
        } else if (password.equals(confirmPassword)) {
            this.password = password;
            return true;
        } else {
            return false;
        }

    }


    public String checkRadioButton(View v) {
        if (v == clientRadioButton) {
            accountType = String.valueOf(1);
        }
        if (v == serviceProviderRadioButton) {
            accountType = String.valueOf(2);
        }
        return accountType;
    }

    private boolean validateEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email = email;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == next) {
//            Toast.makeText(RegistrationMainDataEntry.this, "hey you", Toast.LENGTH_LONG).show();
            if (validateName(name_et.getText().toString()) && validatePhoneNumber(phone_et.getText().toString()) &&
                    validateEmail(email_et.getText().toString()) &&
                    validatePassword(password_et.getText().toString(), confirmPassword_et.getText().toString())) {
//              Toast.makeText(RegistrationMainDataEntry.this, "data is correct",Toast.LENGTH_LONG).show();
                if (TextUtils.isEmpty(accountType)) {
                    Toast.makeText(RegistrationMainDataEntry.this, "please choose account type", Toast.LENGTH_LONG).show();
                } else {
                    sendData();
                }

            } else {
                if (!validateName(name_et.getText().toString())) {
                    name_et.setError(getString(R.string.nameValidationError));
                    name_et.requestFocus();
                }
                if (!validatePhoneNumber(phone_et.getText().toString())) {
                    phone_et.setError(getString(R.string.phoneValidationError));
                    phone_et.requestFocus();
                }
                if (!validatePassword(password_et.getText().toString(), confirmPassword_et.getText().toString())) {
                    if (password == null || password == "") {
                        password_et.setError(getString(R.string.passwordEmpty));
                    } else {
                        confirmPassword_et.setError(getString(R.string.passwordValidationError));
                        phone_et.requestFocus();
                    }
                }
                if (!validateEmail(email_et.getText().toString())) {
                    email_et.setError(getString(R.string.emailValidationError));
                    email_et.requestFocus();
                }

            }
        }
    }

    private void sendData() {

        stringRequest = new StringRequest(Request.Method.POST, BACK_END_REGISTERATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                register = gson.fromJson(response, Register.class);

                if (register != null) {
                    UserId = register.getResult().getUserId();
                }

                PreferencesUtils.saveId(String.valueOf(UserId), RegistrationMainDataEntry.this);
                PreferencesUtils.saveName(name, RegistrationMainDataEntry.this);
//                 Log.i("RIGISTERRESPONSE",response);
                switch (accountType) {
                    case "user_data":
                        Intent intent = new Intent(RegistrationMainDataEntry.this, ClientData.class);
                        PreferencesUtils.saveAccountType("user_data", RegistrationMainDataEntry.this);
                        startActivity(intent);
                        break;
                    case "address":
                        Intent intent2 = new Intent(RegistrationMainDataEntry.this, ServiceProviderEntry.class);
                        PreferencesUtils.saveAccountType("address", RegistrationMainDataEntry.this);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationMainDataEntry.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                NetworkResponse networkResponse = error.networkResponse;

                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Snackbar.make(scrollView, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        sendData();
                                    }
                                }).show();
                        // Toast.makeText(GroupSettings.this, errorMessage, Toast.LENGTH_LONG).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        Snackbar.make(scrollView, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        sendData();
                                    }
                                }).show();
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("error");
                        String message = response.getString("message");
                        Log.e("Error Status", status);
                        Log.e("Error Message", message);
                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                            Snackbar.make(scrollView, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendData();
                                        }
                                    }).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                            Toast.makeText(RegistrationMainDataEntry.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                            Toast.makeText(RegistrationMainDataEntry.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                            Snackbar.make(scrollView, errorMessage, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendData();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                params.put("terms", terms);
                params.put("iqama_no", "5");
                params.put("user_type", accountType);
                params.put("mobile_id", deviceToken);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class getToken extends FirebaseInstanceIdService {
        @Override
        public void onTokenRefresh() {
            refreshedTokenid = FirebaseInstanceId.getInstance().getToken();
        }
    }
}
