package com.example.appandroidmaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivitymaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etSearch;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final String API_KEY = "AIzaSyDeFjriNg338RJ4CPcSQixJqsPdNhEySkw";
    private static final String TAG = "MainActivitymaps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitymaps);

        // Inicializa Places API con tu API Key
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        // Configura el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inicializa el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Configuración del buscador de lugares
        etSearch = findViewById(R.id.et_search);
        etSearch.setFocusable(false);  // Evita que el teclado aparezca al hacer clic
        etSearch.setOnClickListener(v -> {
            // Lista de campos que queremos del lugar
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            // Inicia la actividad de autocompletar
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(MainActivitymaps.this);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Solicita permiso de ubicación si no está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                Task<Location> locationTask = fusedLocationClient.getLastLocation();
                locationTask.addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        searchNearbyRestaurants(currentLocation);
                    } else {
                        Toast.makeText(MainActivitymaps.this, "No se pudo obtener la ubicación actual.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    // Maneja el error aquí
                    Toast.makeText(MainActivitymaps.this, "Error al obtener la ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (SecurityException e) {
                // Maneja la excepción aquí
                Toast.makeText(MainActivitymaps.this, "Permiso de ubicación no concedido: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Solicita permiso de ubicación si no está concedido
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void searchNearbyRestaurants(LatLng currentLocation) {
        OkHttpClient client = new OkHttpClient();

        // Construye la URL para la solicitud de la API de Places
        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=2000&type=restaurant&key=%s",
                currentLocation.latitude, currentLocation.longitude, API_KEY);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivitymaps.this, "Error al buscar lugares: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(MainActivitymaps.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show());
                    return;
                }

                String responseData = response.body().string();
                Log.d(TAG, "Response: " + responseData);

                runOnUiThread(() -> {
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        JSONArray results = jsonObject.getJSONArray("results");

                        // Limpia los marcadores anteriores en el mapa
                        mMap.clear();

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject place = results.getJSONObject(i);
                            String name = place.getString("name");
                            JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            LatLng placeLatLng = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(placeLatLng).title(name));
                            builder.include(placeLatLng);
                        }

                        // Ajusta la cámara para mostrar todos los lugares encontrados
                        if (results.length() > 0) {
                            LatLngBounds bounds = builder.build();
                            int padding = 50; // Ajusta el padding según tus necesidades
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivitymaps.this, "Error al procesar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
