package com.example.randomuserlocation;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView latitude;
    private TextView longitude;
    private ProgressDialog load;
    private GoogleMap mMap;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //latitude = (TextView)findViewById(R.id.latitudeText);
        //longitude = (TextView)findViewById(R.id.longitudeText);

        acionaRandom();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void acionaRandom(){
        GetJson download = new GetJson();
        //Chama Async Task

        download.execute();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latlng = new LatLng(-30, -51);
        final Marker marker= mMap.addMarker(new MarkerOptions().position(latlng).title("Marker in local position"));

        try{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                    marker.setPosition(latlng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class GetJson extends AsyncTask<Void, Void, Person> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Person doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacao("https://randomuser.me/api");
        }

        @Override
        protected void onPostExecute(Person pessoa){
            //latitude.setText(pessoa.getLatitude());
            //longitude.setText(pessoa.getLongitude());
            person = pessoa;

            load.dismiss();
        }
    }
}