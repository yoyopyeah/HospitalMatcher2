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
        //
        Location locationGPS = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //user's location
        double longitude = locationGPS.getLongitude();
        double latitude = locationGPS.getLatitude();

        TextView userLocation = (TextView) findViewById(R.id.text1);

        userLocation.setText( longitude + "\t" + latitude);

        Location hospitalLocation = new Location(""); //info on how constructor works here: https://stackoverflow.com/questions/17983865/making-a-location-object-in-android-with-latitude-and-longitude-values
        hospitalLocation.setLatitude(-120d); //insert latitude here
        hospitalLocation.setLongitude(37d);
        //calculating the distance between points using distanceTo

        float distance = locationGPS.distanceTo(hospitalLocation); // the distance between the two locations in meters

        TextView distanceText = (TextView) findViewById(R.id.text3);
        distanceText.setText("Nearest hospital is " + distance);
        //onclick


    }

}