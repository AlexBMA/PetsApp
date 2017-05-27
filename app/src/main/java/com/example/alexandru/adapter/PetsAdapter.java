package com.example.alexandru.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alexandru.model.Pet;
import com.example.alexandru.petsapp.R;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Alexandru on 5/21/2017.
 */

public class PetsAdapter extends ArrayAdapter<Pet> {


    public PetsAdapter(@NonNull Context context, @NonNull List<Pet> objects) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pet, parent, false);
        }

        Pet tempPet = getItem(position);

        TextView textViewName = (TextView) listItemView.findViewById(R.id.name);
        textViewName.setText(tempPet.getName());

        TextView textViewBreed = (TextView) listItemView.findViewById(R.id.summary);
        textViewBreed.setText(tempPet.getBreed());


        return listItemView;
    }
}
