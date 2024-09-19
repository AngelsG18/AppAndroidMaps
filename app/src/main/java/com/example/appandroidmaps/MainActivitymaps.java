package com.example.appandroidmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivitymaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitymaps);

        // Inicializa Places API con tu API Key
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDeFjriNg338RJ4CPcSQixJqsPdNhEySkw");
        }

        // Configura el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Configuración del buscador de lugares
        etSearch = findViewById(R.id.et_search);
        etSearch.setFocusable(false);  // Evita que el teclado aparezca al hacer clic
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lista de campos que queremos del lugar
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                // Inicia la actividad de autocompletar
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(MainActivitymaps.this);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configura un marcador inicial
        LatLng senati = new LatLng(-11.999585124005963, -77.06186583919435);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(senati, 12));
        mMap.addMarker(new MarkerOptions().position(senati).title("Ubicado SENATI!!!"));
    }

    // Maneja el resultado de la actividad de autocompletar
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Obtiene el lugar seleccionado
            Place place = Autocomplete.getPlaceFromIntent(data);
            LatLng latLng = place.getLatLng();

            if (latLng != null) {
                // Mueve la cámara y añade un marcador en el lugar seleccionado
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
                etSearch.setText(place.getName());
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Maneja el error
        }
    }
}
