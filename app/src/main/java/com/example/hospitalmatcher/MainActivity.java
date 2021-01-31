package com.example.hospitalmatcher;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //source: https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android

        LocationManager mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // this is to suppress the error, the permission will be set in the android virtual device before this runs
            return;
        }

        // Getting user's location
        Location locationGPS = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double userLongitude = locationGPS.getLongitude();
        double userLatitude = locationGPS.getLatitude();

        // create a new HospitalDataBase
        HospitalDataBase dataBase = new HospitalDataBase(userLatitude, userLongitude);


        // not sure what this is
        TextView userLocation = (TextView) findViewById(R.id.text1);

        // show user's location on App screen
        userLocation.setText(userLongitude + "\t" + userLatitude);

        // not sure what this is
        TextView distanceText = (TextView) findViewById(R.id.text3);


        String nearest = dataBase.getHospitalsNames().get(0);
        distanceText.setText("The nearest hospital is:" + nearest);
        //onclick


    }

}