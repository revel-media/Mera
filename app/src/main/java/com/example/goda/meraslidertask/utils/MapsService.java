package com.example.goda.meraslidertask.utils;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.goda.meraslidertask.models.UserLocation;
import com.example.goda.meraslidertask.view.LoginActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsService extends Service {
    public MapsService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        DatabaseReference databaseReference ;
        LocationManager locationManager;
        double longitude, latitude;
        private LocationRequest mLocationRequest;
        private int userId;
        private String name;
        private long UPDATE_INTERVAL = 10 * 1000;  /* _10 secs */
        private long FASTEST_INTERVAL = 2000; /* address sec */


        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            locationManager = (LocationManager) getSystemService(MapsService.this.LOCATION_SERVICE);

            startLocationUpdates();
            databaseReference = FirebaseDatabase.getInstance().getReference("userLocation");

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
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

            SettingsClient settingsClient = LocationServices.getSettingsClient(MapsService.this);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            // new Google API SDK v11 uses getFusedLocationProviderClient(this)

            if (ActivityCompat.checkSelfPermission(MapsService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            getFusedLocationProviderClient(MapsService.this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
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
            updateLocation();
            PreferencesUtils.saveLat(String.valueOf(latitude),MapsService.this);
            PreferencesUtils.saveLng(String.valueOf(longitude),MapsService.this);
        }

        private void updateLocation(){

//            final UserLocation newuserLocation = new UserLocation(latitude, longitude, userId, );
            userId = Integer.valueOf(PreferencesUtils.getid(MapsService.this));
//            name = PreferencesUtils.getName(MapsService.this);
            databaseReference.child(String.valueOf(userId)).child("lat").setValue(latitude);
            databaseReference.child(String.valueOf(userId)).child("lng").setValue(longitude);
            PreferencesUtils.updateLatAndLng(MapsService.this, String.valueOf(latitude), String.valueOf(longitude));
        }
    }


    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
