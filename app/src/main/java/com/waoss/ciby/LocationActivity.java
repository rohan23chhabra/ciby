package com.waoss.ciby;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.waoss.ciby.apis.UserType;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;
    private LatLng markedLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private UserType userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        userType = UserType.valueOf(String.valueOf(getIntent().getExtras().getString("user-type")));
        Log.d("user-type-on-map", userType.toString());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.googleMap.setMyLocationEnabled(true);
            setCurrentLocationOnMap();
        } else {
            ActivityCompat
                    .requestPermissions(LocationActivity.this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }

        googleMap.setOnMapClickListener(latLng -> {

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(latLng.latitude + " : " + latLng.longitude);

            // Clears the previously touched position
            googleMap.clear();

            // Animating to the touched position
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // Placing a marker on the touched position
            googleMap.addMarker(markerOptions);

            Log.i("lodu-1", "mark ho gya.");
            Log.i("lodu-2", latLng.toString());

            markedLocation = latLng;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setCurrentLocationOnMap();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void setCurrentLocationOnMap() {
        googleMap.setMyLocationEnabled(true);
        Log.d("permission111", "Permission granted");
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(
                        this,
                        location -> {
                            if (location != null) {
                                Log.d("location111",
                                        "Current location.." +
                                                ".yay");
                                LatLng currentLocation =
                                        new LatLng(
                                                location.getLatitude(),
                                                location.getLongitude());
                                googleMap.addMarker(
                                        new MarkerOptions()
                                                .position(
                                                        currentLocation)
                                                .title("Current Location"));
                                googleMap.moveCamera(
                                        CameraUpdateFactory
                                                .newLatLng(
                                                        currentLocation));

                                googleMap.animateCamera(
                                        CameraUpdateFactory
                                                .newLatLngZoom(
                                                        new LatLng(
                                                                location.getLatitude(),
                                                                location.getLongitude()),
                                                        13));

                                CameraPosition cameraPosition =
                                        new CameraPosition.Builder()
                                                .target(new LatLng(
                                                        location.getLatitude(),
                                                        location.getLongitude()))
                                                .zoom(17)
                                                .bearing(90)
                                                .tilt(40)
                                                .build();

                                googleMap.animateCamera(
                                        CameraUpdateFactory
                                                .newCameraPosition(
                                                        cameraPosition));
                            }
                        });
    }

    public void setFinalLocationOnClick(View view) {
        Log.d("set-location-button", "click hua");
        showDestinationActivity();
    }

    private void showDestinationActivity() {
        final Intent intent;
        if (userType == UserType.MIGRATING_WORKER) {
            intent = new Intent(LocationActivity.this, EmergencyActivity.class);
            intent.putExtra("location", markedLocation);
        } else {
            intent = new Intent(LocationActivity.this, LoginActivity.class);
            intent.putExtra("location", markedLocation);
            intent.putExtra("user-type", userType.toString());
        }
        startActivity(intent);
    }
}
