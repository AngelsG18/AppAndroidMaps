package com.example.appandroidmaps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivitymaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnNormal;
    private Button btnSatellite;
    private Button btnLima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitymaps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnNormal = findViewById(R.id.btn_vista);
        btnSatellite = findViewById(R.id.btn_satelital);
        btnLima = findViewById(R.id.btn_lima);

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });

        btnLima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    LatLng comasYork = new LatLng(-11.930310, -77.052760);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(comasYork, 15));
                    mMap.addMarker(new MarkerOptions().position(comasYork).title("Ubicado en una casa, que para nada es la mia"));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng senati = new LatLng(-11.999585124005963, -77.06186583919435);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(senati, 12));
        mMap.addMarker(new MarkerOptions().position(senati).title("Ubicado SENATI!!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(senati));
    }
}