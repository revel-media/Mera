package com.example.goda.meraslidertask.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.example.goda.meraslidertask.utils.PreferencesUtils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class ClientData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.client_citySpinner)
    Spinner spinner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.address_tv)
    EditText addressTv;
    @BindView(R.id.buildingName)
    EditText building;
    @BindView(R.id.floor_number)
    EditText floor;
    @BindView(R.id.clientDataNextButton)
    Button next;
    @BindView(R.id.clientDataScrollview)
    ScrollView scrollView;
    @BindView(R.id.clientDataButton)
    Button clientdataButton;
    @BindView(R.id.client_data_circle_tv)
    TextView client_data_circle_tv;

    private String choosedcity;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private static final String API = "http://mera.live/api/user/user_details";
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    LocationManager locationManager;
    double longitude, latitude;
    private List<String> currentLatAndLong = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.newUserTitle);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        if (PreferencesUtils.getAccountType(this).matches("1")){
            client_data_circle_tv.setText("بيانات العميل");
        }else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) client_data_circle_tv.getLayoutParams();
            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) clientdataButton.getLayoutParams();
            lp.setMargins(0,0,0,0);
            lp2.setMargins(0,0,0,0);

            client_data_circle_tv.setLayoutParams(lp);
            clientdataButton.setLayoutParams(lp2);
            client_data_circle_tv.setText("بيانات مقدم الخدمه");
        }
        startLocationUpdates();
        locationManager = (LocationManager) getSystemService(ClientData.this.LOCATION_SERVICE);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choosedcity = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(this, choosedcity, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "يجب اختيار المدينة", Toast.LENGTH_LONG).show();

    }


    private boolean checkOnAddress(String address) {
        if (address.matches("")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkOnCity(String city) {
        if (city.matches("")) {
            return false;
        } else {
            return true;
        }
    }

    public void next(View view) {
        if (checkOnAddress(addressTv.getText().toString()) && checkOnCity(choosedcity)) {
            sendData();

        } else {

            if (!checkOnAddress(addressTv.getText().toString())) {
                addressTv.setError(getString(R.string.addressValidationError));
                addressTv.requestFocus();
            }
            if (!checkOnCity(choosedcity)) {
                Toast.makeText(this, "يجب اختيار اسم المدينة", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendData() {
        stringRequest = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //saving user data in sharedpreferences
                PreferencesUtils.saveEmail(addressTv.getText().toString(), ClientData.this);
                Intent intent = new Intent(ClientData.this, MapsActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClientData.this, error.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ClientData.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        if (response.getString(result).matches("0")) {
                            Toast.makeText(ClientData.this, "wrong email or password", Toast.LENGTH_LONG).show();
                        } else {
                            String status = response.getString("status");
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
                                Toast.makeText(ClientData.this, errorMessage, Toast.LENGTH_LONG).show();
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message + " Check your inputs";
                                Toast.makeText(ClientData.this, errorMessage, Toast.LENGTH_LONG).show();
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


                params.put("user_id", PreferencesUtils.getid(ClientData.this));
                params.put("addressTv", addressTv.getText().toString());
                params.put("city", choosedcity);
                params.put("building_info", building.getText().toString());
                params.put("skill_ids", "");

                String location = gson.toJson(currentLatAndLong);
                params.put("location", location);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest().create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        latitude=location.getLatitude();
        longitude=location.getLongitude();
//        Toast.makeText(ClientData.this, String.valueOf(latitude+longitude),Toast.LENGTH_LONG).show();
        currentLatAndLong.add(String.valueOf(latitude));
        PreferencesUtils.saveLat(String.valueOf(latitude),ClientData.this);
        currentLatAndLong.add(String.valueOf(longitude));
        PreferencesUtils.saveLng(String.valueOf(longitude),ClientData.this);
    }
}
