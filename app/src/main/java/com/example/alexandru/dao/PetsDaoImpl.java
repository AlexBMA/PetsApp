package com.example.alexandru.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.alexandru.data.PetContact;
import com.example.alexandru.data.PetDbHelper;
import com.example.alexandru.model.Pet;

import java.util.List;

/**
 * Created by Alexandru on 5/12/2017.
 */

public class PetsDaoImpl implements PetsDao<Pet> {


    @Override
    public long insertItemAndGetId(PetDbHelper mDbHelper, Pet item) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PetContact.PetEntry.COLUMN_PET_NAME, item.getName());
        values.put(PetContact.PetEntry.COLUMN_PET_WEIGHT, item.getWeight());
        values.put(PetContact.PetEntry.COLUMN_PET_GENDER, item.getGender());
        values.put(PetContact.PetEntry.COLUMN_PET_BREED, item.getBreed());

        long rez = db.insert(PetContact.PetEntry.TABLE_NAME, null, values);

        return rez;
    }

    @Override
    public void deleteItem(PetDbHelper mDbHelper, long id) {

    }

    @Override
    public void updateItem(PetDbHelper mDbHelper, long id, Pet item) {

    }

    @Override
    public Pet getItem(PetDbHelper mDbHelper, long id) {
        return null;
    }

    @Override
    public List<Pet> getAllItems(PetDbHelper mDbHelper) {
        return null;
    }


}
