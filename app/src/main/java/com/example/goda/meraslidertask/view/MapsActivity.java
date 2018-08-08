package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.models.UserLocation;
import com.example.goda.meraslidertask.utils.PreferencesUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    private List<UserLocation> usersLocation = new ArrayList<>();
    double myLat , myLng, myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("userLocation");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng MyCurrentPlace = new  LatLng(Double.valueOf(PreferencesUtils.getLat(MapsActivity.this)), Double.valueOf(PreferencesUtils.getLng(MapsActivity.this)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(PreferencesUtils.getLat(MapsActivity.this))
        , Double.valueOf(PreferencesUtils.getLng(MapsActivity.this)))).title(PreferencesUtils.getName(MapsActivity.this)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyCurrentPlace));
    }


    private void chooseNearestUsers(){

        // get User location
        myLat = Double.valueOf(PreferencesUtils.getLat(MapsActivity.this));
        myLng = Double.valueOf(PreferencesUtils.getLng(MapsActivity.this));
        myId  = Integer.valueOf(PreferencesUtils.getid(MapsActivity.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                  for (DataSnapshot d : dataSnapshot.getChildren()){
                      UserLocation userLocation = d.getValue(UserLocation.class);
                      usersLocation.add(userLocation);
                  }
                Toast.makeText(MapsActivity.this, "users number"+String.valueOf(usersLocation.size()), Toast.LENGTH_LONG).show();

                if (usersLocation.size()!= 0 && usersLocation .size()!= 1){

                        for (int i = 0; i < usersLocation.size(); i++) {

                            UserLocation user = usersLocation.get(i);
                            if (myId != user.getUserId()) {
                                Toast.makeText(MapsActivity.this, "user id " + String.valueOf(user.getUserId()), Toast.LENGTH_LONG).show();

                                double distance = acos(sin(PI * user.getLat() / 180.0) * sin(PI * myLat / 180.0) + cos(PI * user.getLat() / 180.0) * cos(PI * myLat / 180.0) * cos(PI * user.getLng() / 180.0 - PI * myLng / 180.0)) * 6371;
                                if (distance <= 5000.0) {
                                    LatLng MyCurrentPlace = new LatLng(Double.valueOf(PreferencesUtils.getLat(MapsActivity.this)), Double.valueOf(PreferencesUtils.getLng(MapsActivity.this)));
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(user.getLat()
                                            , user.getLng())).title(user.getName()));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(MyCurrentPlace));
                                }
                            }
                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        chooseNearestUsers();
    }
}
