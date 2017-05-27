package com.example.alexandru.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alexandru.data.PetContact;
import com.example.alexandru.petsapp.R;

/**
 * Created by Alexandru on 5/27/2017.
 */

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvSummary = (TextView) view.findViewById(R.id.summary);

        String name = cursor.getString(cursor.getColumnIndex(PetContact.PetEntry.COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndex(PetContact.PetEntry.COLUMN_PET_BREED));

        int gender = cursor.getInt(cursor.getColumnIndex(PetContact.PetEntry.COLUMN_PET_GENDER));
        int weight = cursor.getInt(cursor.getColumnIndex(PetContact.PetEntry.COLUMN_PET_WEIGHT));

        tvName.setText(name);
        String summary = constructSummary(breed, gender, weight);
        tvSummary.setText(summary);

    }


    private String constructSummary(String breed, int gender, int weight) {
        String genderText = translateGender(gender);

        String summary = breed + " " + genderText + " " + weight;

        return summary;
    }

    private String translateGender(int genderNr) {
        if (PetContact.PetEntry.GENDER_MALE == genderNr) return "male";
        if (PetContact.PetEntry.GENDER_FEMALE == genderNr) return "female";
        // PetContact.PetEntry.GENDER_UNKNOWN
        return "unknown";
    }
}
