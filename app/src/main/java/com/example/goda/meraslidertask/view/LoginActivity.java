package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.goda.meraslidertask.models.UserLocation;
import com.example.goda.meraslidertask.models.login.Login;
import com.example.goda.meraslidertask.models.login.LoginResults;
import com.example.goda.meraslidertask.utils.PreferencesUtils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


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
    TextView forgotPassword;


    private RequestQueue requestQueue ;
    private StringRequest stringRequest;
    private String BACK_END_LOGIN = "http://mera.live/api/user/login";
    private Login login ;
    private Gson gson;
    private LoginResults loginResults;
    private String account_type;
    LocationManager locationManager;
    double longitude, latitude;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* _10 secs */
    private long FASTEST_INTERVAL = 2000; /* address sec */
    DatabaseReference databaseReference ;
    private int userId;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        requestQueue= Volley.newRequestQueue(this);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        login = new Login();

        //get user location
        locationManager = (LocationManager) getSystemService(LoginActivity.this.LOCATION_SERVICE);
        startLocationUpdates();

        databaseReference = FirebaseDatabase.getInstance().getReference("userLocation");
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            sendData();
        }else if (view ==register_btn){
            Intent intent = new Intent(LoginActivity.this, RegisterationConditions.class);
            startActivity(intent);
        }else if(view ==forgotPassword){
            Intent intent = new Intent(LoginActivity.this, ChangePassword.class);
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
                    loginResults = login.getResult();
                    account_type = loginResults.getTypeID();
                    userId = Integer.valueOf(loginResults.getUserID());
                    name = loginResults.getFullname();

                    //save data in sharedPreferences
                    PreferencesUtils.saveId(String.valueOf(userId), LoginActivity.this);
                    PreferencesUtils.saveName(name, LoginActivity.this);

                    // add user Location to firebase
                    addLocation();

                    if (account_type.matches("1")){
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
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

    private void addLocation(){
       final UserLocation newuserLocation = new UserLocation(latitude, longitude, userId, name );
       databaseReference.push();
       databaseReference.child(String.valueOf(userId)).setValue(newuserLocation);
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        PreferencesUtils.saveLat(String.valueOf(latitude),LoginActivity.this);
        PreferencesUtils.saveLng(String.valueOf(longitude),LoginActivity.this);
    }
}
