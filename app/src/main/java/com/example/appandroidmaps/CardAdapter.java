package com.example.appandroidmaps;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends BaseAdapter {

    private Context context;
    private List<CardItem> cardItemList;
    private List<CardItem> filterCardList;
    private LayoutInflater inflater;


    public CardAdapter(Context context,List<CardItem> cardItemList){
        this.context = context;
        this.cardItemList =new ArrayList<>(cardItemList) ;
        this.filterCardList = new ArrayList<>(cardItemList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return   filterCardList.size();
    }

    @Override
    public Object getItem(int i) {
        return filterCardList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return  i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_component, parent, false);
        }

        CardItem cardItem = filterCardList.get(position);

        ImageView backgroundImageView = convertView.findViewById(R.id.card_image);
        backgroundImageView.setImageResource(cardItem.getImageResId());

        TextView titleTextView = convertView.findViewById(R.id.card_title);
        titleTextView.setText(cardItem.getTitle());

        TextView idTextView = convertView.findViewById(R.id.city_id);
        idTextView.setText(String.valueOf(cardItem.getId()));

        return convertView;
    }

    // Función de normalización de texto
    private String removeDiacritics(String text) {
        if (text == null) {
            return "";
        }
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", ""); // Elimina los caracteres diacríticos
    }

    public void filter(String query) {
        String normalizedQuery = removeDiacritics(query.toLowerCase().trim());
        filterCardList.clear();

        if (normalizedQuery.isEmpty()) {
            filterCardList.addAll(cardItemList); // Mostrar todos los elementos si el texto está vacío
        } else {
            Log.d(TAG, normalizedQuery);
            for (CardItem item : cardItemList) {
                if (item.getTitle().toLowerCase().contains(normalizedQuery)) {
                    filterCardList.add(item); // Agregar solo los elementos que coincidan con la búsqueda
                }
            }
        }
        notifyDataSetChanged(); // Refresca el ListView
    }

}
