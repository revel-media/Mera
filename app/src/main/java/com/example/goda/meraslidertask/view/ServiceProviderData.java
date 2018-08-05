package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.Manifest;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.utils.PreferencesUtils;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.SettingsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceProviderData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.serviceregisterationConditions)
    Button conditionsActivityButton;
    @BindView(R.id.servicemainDataActivityButton)
    Button maindataButton;
    @BindView(R.id.serviceclientDataButton)
    Button clientdataButton;
    @BindView(R.id.servicetheEndButton)
    Button theendButton;
    @BindView(R.id.serviceNext)
    Button next;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.laundry)
    RadioButton laundry;
    @BindView(R.id.houseCleaning)
    RadioButton houseCleaning;
    @BindView(R.id.ironingClothes)
    RadioButton ironingClothes;
    @BindView(R.id.babySitting)
    RadioButton babySitting;
    @BindView(R.id.careOfelderly)
    RadioButton careOfelderly;
    @BindView(R.id.cooking)
    RadioButton cooking;
    @BindView(R.id.client_citySpinner)
    Spinner spinner;
    @BindView(R.id.address_tv)
    EditText address_tv;
    @BindView(R.id.neighborhood_et)
    EditText buildingName;
    @BindView(R.id.client_data_circle_tv)
    TextView client_data_circle_tv;

    private String choosedcity;
    private List<Integer> choosedSkiils = new ArrayList<>();
    private String tv_name;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private static final String API = "http://mera.live/api/user/user_details";
//    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    LocationManager locationManager;
    double longitude, latitude;
    private List<String> currentLatAndLong = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.newUserTitle);


        conditionsActivityButton.setOnClickListener(this);
        maindataButton.setOnClickListener(this);
        clientdataButton.setOnClickListener(this);
        theendButton.setOnClickListener(this);
        next.setOnClickListener(this);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        //Display previous user data if existed
        String address = PreferencesUtils.getAddress(ServiceProviderData.this);
        if (address != null && address != "") {
            address_tv.setText(PreferencesUtils.getAddress(ServiceProviderData.this));
            buildingName.setText(PreferencesUtils.getBuildingNumber(ServiceProviderData.this));
        }

        tv_name = getIntent().getStringExtra("textView_name");
        client_data_circle_tv.setText(tv_name);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
//        startLocationUpdates();

        locationManager = (LocationManager) getSystemService(ServiceProviderData.this.LOCATION_SERVICE);
    }

    public void checkRadioButton(View v) {
        if (v == laundry) {
            choosedSkiils.add(1);
        }
        if (v == houseCleaning) {
            choosedcity = (String) houseCleaning.getText();
            choosedSkiils.add(2);
        }
        if (v == ironingClothes) {
            choosedcity = (String) ironingClothes.getText();
            choosedSkiils.add(3);
        }
        if (v == babySitting) {
            choosedcity = (String) babySitting.getText();
            choosedSkiils.add(4);
        }
        if (v == careOfelderly) {
            choosedcity = (String) careOfelderly.getText();
            choosedSkiils.add(5);
        }
        if (v == cooking) {
            choosedcity = (String) cooking.getText();
            choosedSkiils.add(6);
        }
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

    @Override
    public void onClick(View view) {
        // Condition activity
        if (view == conditionsActivityButton) {
            Intent intent = new Intent(ServiceProviderData.this, RegisterationConditions.class);
            startActivity(intent);

        }else if (view == maindataButton) {
            Intent intent = new Intent(ServiceProviderData.this, RegistrationMainDataEntry.class);
            startActivity(intent);

            //Next Button
        } else if (view == next) {
            if (checkOnAddress(address_tv.getText().toString()) && checkOnCity(choosedcity)) {
                senData();

            } else {

                if (!checkOnAddress(address_tv.getText().toString())) {
                    address_tv.setError(getString(R.string.addressValidationError));
                    address_tv.requestFocus();
                }
                if (!checkOnCity(choosedcity)) {
                    Toast.makeText(this, "يجب اختيار اسم المدينة", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choosedcity = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, choosedcity, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "يجب اختيار المدينة", Toast.LENGTH_LONG).show();

    }

    private void senData() {
        stringRequest = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //saving user data in sharedpreferences
                PreferencesUtils.saveEmail(address_tv.getText().toString(), ServiceProviderData.this);
                PreferencesUtils.saveName(buildingName.getText().toString(), ServiceProviderData.this);
                Intent intent = new Intent(ServiceProviderData.this, TheEnd.class);
                intent.putExtra("textView_name", tv_name);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Map<String, String> params = new HashMap<>();


                params.put("user_id", PreferencesUtils.getid(ServiceProviderData.this));
                params.put("address", address_tv.getText().toString());
                params.put("city", choosedcity);
                params.put("building_info", buildingName.getText().toString());

                //converting ArrayList to String
                String skills = gson.toJson(choosedSkiils);
                params.put("skill_ids", skills);

                String location = gson.toJson(currentLatAndLong);
                params.put("location", location);


            }
        });
        requestQueue.add(stringRequest);
    }

//    // Trigger new location updates at interval
//    protected void startLocationUpdates() {
//
//        // Create the location request to start receiving updates
//        mLocationRequest = new LocationRequest().create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//
//        // Create LocationSettingsRequest object using location request
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        LocationSettingsRequest locationSettingsRequest = builder.build();
//
//        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
//        settingsClient.checkLocationSettings(locationSettingsRequest);
//
//        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        onLocationChanged(locationResult.getLastLocation());
//                    }
//                },
//                Looper.myLooper());
//    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        currentLatAndLong.add(String.valueOf(latitude));
        currentLatAndLong.add(String.valueOf(longitude));
    }
}
