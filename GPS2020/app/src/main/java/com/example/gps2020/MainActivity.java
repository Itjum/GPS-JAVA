package com.example.gps2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtLat, txtLong;
    LocationManager locMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLat = findViewById(R.id.txtLat);
        txtLong = findViewById(R.id.txtLong);

        locMan = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

    }//onCreate

    @Override
    protected void onStart(){
        super.onStart();

        createLocationListener();
    } //on Start

    @Override
    protected void onStop(){
        super.onStop();

        locMan = null;
    }

    private void createLocationListener(){
       try{
           locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, new LocationListener() {
               @Override
               public void onLocationChanged(Location location) {

                   txtLat.setText("" + location.getLatitude());
                   txtLong.setText("" + location.getLongitude());
               }

               @Override
               public void onProviderEnabled(String provider){

                   if(provider == LocationManager.GPS_PROVIDER){
                       //Show last known.
                       Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                       if(loc != null) {
                           //Now if the location is not null just show it.
                           txtLat.setText("" + loc.getLatitude());
                           txtLong.setText("" + loc.getLongitude());
                       }//inner if

                   }//outer if

               }//onProviderEnabled()

               @Override
               public void onProviderDisabled(String provider){

                   if(provider != LocationManager.GPS_PROVIDER){
                       //Just show default string.
                       txtLat.setText("Currently unavailable");
                       txtLong.setText("Currently unavailable");

                   }//if

               }//onProviderDisabled()


               public void onStatusChanged(String provider, int status, Bundle extras){

               }//onStatusChanged()

           });

       }// try
        catch(SecurityException se){
            se.printStackTrace();
        }//catch
    }


}//activity class