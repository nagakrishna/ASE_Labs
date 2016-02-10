package com.example.nagakrishna.ase_lab3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude, longitude;
    private Bitmap bmp;
    //private String Address;
    public Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle extras = getIntent().getExtras();
        bmp = (Bitmap)extras.get("image");
        //Address = (String)extras.get("Address");
       // Bitmap bmp = (Bitmap) getIntent().getParcelableExtra("image_camera");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;
        GetGPS getGPS = new GetGPS(this);
        latitude = getGPS.GetLatitude();
        longitude = getGPS.GetLongitude();
        LatLng newLocation = new LatLng(latitude, longitude);
        geocoder = new Geocoder(this);
        StringBuilder userAddress;



     try {
         List<android.location.Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
         Address address = addresses.get(0);
         userAddress =  new StringBuilder();
         for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
             userAddress.append(address.getAddressLine(i)).append("\t");
         }
         userAddress.append(address.getCountryName()).append("\t");
         BitmapDescriptor iconBitmap = BitmapDescriptorFactory.fromBitmap(bmp);
         mMap.addMarker(new MarkerOptions().position(newLocation).title(String.valueOf(userAddress)).icon(iconBitmap));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
         mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("Maps MArker", e.getMessage().toString());
        }
    }

}
