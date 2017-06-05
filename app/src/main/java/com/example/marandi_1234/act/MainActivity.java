package com.example.marandi_1234.act;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    //all variables goes here

    //commiting changes
    private final String LOG_TAG = "EMPTY_NOW";
    private EditText e1;
    private EditText e2;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderApi mLocationProvider = LocationServices.FusedLocationApi;
    private Double mlat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);


        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000); // update every 1seconds
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
        //txtout.setText("connected");
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //txtout.setText("123");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]  {Manifest.permission.ACCESS_FINE_LOCATION},101);
            }
            return;
        }
        //txtout.setText("inside request location");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG ,"Connection is Suspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG ,"Connection is Suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        //requestLocationUpdates();
        Log.i(LOG_TAG , "location Changed");
       // txtout.setText("inside location changed");
        mlat = location.getLatitude();
        //txtout.setText(location.toString());
        e1.setText(mlat.toString());
        mlat = location.getLongitude();
        e2.setText(mlat.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mGoogleApiClient.isConnected()){
            requestLocationUpdates();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient , this);
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
