package com.example.appandroidmaps;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class buscadorActivity extends AppCompatActivity {

    private ListView listView;
    private CardAdapter adapter;
    private List<CardItem> cardItemList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        EditText searchBar = findViewById(R.id.buscador);
        listView = findViewById(R.id.lista_item);

        // Inicializar la lista de ítems
        cardItemList = new ArrayList<>();
        cardItemList.add(new CardItem(1, "Perú", R.drawable.india));
        cardItemList.add(new CardItem(2, "México", R.drawable.india));
        cardItemList.add(new CardItem(3, "Brasil", R.drawable.india));
        cardItemList.add(new CardItem(4, "Argentina", R.drawable.india));
        cardItemList.add(new CardItem(5, "Colombia", R.drawable.india));
        cardItemList.add(new CardItem(1, "Perú", R.drawable.india));
        cardItemList.add(new CardItem(2, "México", R.drawable.india));
        cardItemList.add(new CardItem(3, "Brasil", R.drawable.india));
        cardItemList.add(new CardItem(4, "Argentina", R.drawable.india));
        cardItemList.add(new CardItem(5, "Colombia", R.drawable.india));
        cardItemList.add(new CardItem(1, "Perú", R.drawable.india));
        cardItemList.add(new CardItem(2, "México", R.drawable.india));
        cardItemList.add(new CardItem(3, "Brasil", R.drawable.india));
        cardItemList.add(new CardItem(4, "Argentina", R.drawable.india));
        cardItemList.add(new CardItem(5, "Colombia", R.drawable.india));
        cardItemList.add(new CardItem(1, "Perú", R.drawable.india));
        cardItemList.add(new CardItem(2, "México", R.drawable.india));
        cardItemList.add(new CardItem(3, "Brasil", R.drawable.india));
        cardItemList.add(new CardItem(4, "Argentina", R.drawable.india));
        cardItemList.add(new CardItem(5, "Colombia", R.drawable.india));


        // Configurar el ListView
        listView = findViewById(R.id.lista_item);
        adapter = new CardAdapter(this, cardItemList);
        listView.setAdapter(adapter);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No hacer nada antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    Log.d(TAG, s.toString());
                }
                // Filtrar la lista si se ha escrito algo o mostrar todos los elementos si está vacío
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No hacer nada después de que el texto cambie
            }
        });

    }
    /*private ArrayAdapter adapter;
    private List<String> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // fix el ListView
        itemList = new ArrayList<>();
        itemList.add("Manzana");
        itemList.add("Banana");
        itemList.add("Cereza");
        itemList.add("Durazno");
        itemList.add("Uva");

        ListView listView = findViewById(R.id.lista_item);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        EditText searchInput = findViewById(R.id.buscador);
        searchInput.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filtrar la lista
                adapter.getFilter().filter(s);
            }

            public void afterTextChanged(Editable s) {}
        });
    }*/
}