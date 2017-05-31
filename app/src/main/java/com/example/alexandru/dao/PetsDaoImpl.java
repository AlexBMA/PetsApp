package com.example.alexandru.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.alexandru.data.PetContact;
import com.example.alexandru.data.PetDbHelper;
import com.example.alexandru.model.Pet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandru on 5/12/2017.
 */

public class PetsDaoImpl implements PetsDao<Pet> {


    @Override
    public long insertItemAndGetId(PetDbHelper mDbHelper, Pet item) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        setDataForInsert(item, values);

        long rez = db.insert(PetContact.PetEntry.TABLE_NAME, null, values);
        return rez;
    }


    @Override
    public void deleteItem(PetDbHelper mDbHelper, long id) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = PetContact.PetEntry._ID + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = {"" + id};

        // Issue SQL statement.
        db.delete(PetContact.PetEntry.TABLE_NAME, selection, selectionArgs);


    }

    @Override
    public void updateItem(PetDbHelper mDbHelper, long id, Pet item) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();

        setDataForUpdate(item, values);

        // Define 'where' part of query.
        String selection = PetContact.PetEntry._ID + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = {"" + id};

        db.update(PetContact.PetEntry.TABLE_NAME, values, selection, selectionArgs);


    }

    @Override
    public Pet getItem(PetDbHelper mDbHelper, long id) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = PetContact.PetEntry._ID + " = ?";
        String[] selectionArgs = {id + " "};


        Cursor c = db.query(PetContact.PetEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        Pet temp = new Pet();
        c.moveToFirst();
        putDataInObject(c, temp);
        c.close();
        Log.e("Single pet", temp.toString());

        return temp;
    }

    @Override
    public List<Pet> getAllItems(PetDbHelper mDbHelper) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetContact.PetEntry._ID,
                PetContact.PetEntry.COLUMN_PET_NAME,
                PetContact.PetEntry.COLUMN_PET_BREED,
                PetContact.PetEntry.COLUMN_PET_WEIGHT,
                PetContact.PetEntry.COLUMN_PET_GENDER
        };



        List<Pet> listPets = new ArrayList<>();

        Cursor c = db.query(PetContact.PetEntry.TABLE_NAME, projection, null, null, null, null, null);

        int size = c.getCount();
        Pet temp;

        Log.e(" count ", c.getCount() + "");

        for (int i = 0; i < size; i++) {

            c.moveToPosition(i);
            temp = new Pet();
            putDataInObject(c, temp);
            Log.e(" temp ", temp.toString() + "");
            listPets.add(temp);
        }

        c.close();
        Log.e("List", listPets.toString());

        return listPets;
    }

    private void putDataInObject(Cursor c, Pet temp) {
        int tempIndex;

        tempIndex = c.getColumnIndex(PetContact.PetEntry._ID);
        temp.setId(c.getLong(tempIndex));
        tempIndex = c.getColumnIndex(PetContact.PetEntry.COLUMN_PET_NAME);
        temp.setName(c.getString(tempIndex));
        tempIndex = c.getColumnIndex(PetContact.PetEntry.COLUMN_PET_BREED);
        temp.setBreed(c.getString(tempIndex));
        tempIndex = c.getColumnIndex(PetContact.PetEntry.COLUMN_PET_WEIGHT);
        temp.setWeight(c.getInt(tempIndex));
        tempIndex = c.getColumnIndex(PetContact.PetEntry.COLUMN_PET_GENDER);
        temp.setGender(c.getInt(tempIndex));
    }

    private void setDataForInsert(Pet item, ContentValues values) {
        values.put(PetContact.PetEntry.COLUMN_PET_NAME, item.getName());
        values.put(PetContact.PetEntry.COLUMN_PET_WEIGHT, item.getWeight());
        values.put(PetContact.PetEntry.COLUMN_PET_GENDER, item.getGender());
        values.put(PetContact.PetEntry.COLUMN_PET_BREED, item.getBreed());
    }

    private void setDataForUpdate(Pet item, ContentValues values) {
        setDataForInsert(item, values);
    }

    @Override
    public long insertItemAndGetIdWithContentResolver(ContentResolver contentResolver, Pet item) {

        // New value for one column
        ContentValues values = new ContentValues();
        setDataForUpdate(item, values);

        Uri uri = contentResolver.insert(PetContact.PetEntry.CONTENT_URI, values);


        int newId = Integer.parseInt(uri.getLastPathSegment().trim());
        return newId;
    }

    @Override
    public void deleteItemWithContentResolver(ContentResolver contentResolver, long id) {

        String selection = PetContact.PetEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};

        contentResolver.delete(PetContact.PetEntry.CONTENT_URI, selection, selectionArgs);
    }

    @Override
    public void updateItemWithContentResolver(ContentResolver contentResolver, long id, Pet item) {

        ContentValues values = new ContentValues();
        setDataForUpdate(item, values);

        String selection = PetContact.PetEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};

        contentResolver.update(PetContact.PetEntry.CONTENT_URI, values, selection, selectionArgs);

    }

    @Override
    public Pet getItemWithContentResolver(ContentResolver contentResolver, long id) {
        return null;
    }

    @Override
    public Cursor getAllItemsContentResolver(ContentResolver contentResolver) {


        String[] projection = {
                PetContact.PetEntry._ID,
                PetContact.PetEntry.COLUMN_PET_NAME,
                PetContact.PetEntry.COLUMN_PET_BREED,
                PetContact.PetEntry.COLUMN_PET_WEIGHT,
                PetContact.PetEntry.COLUMN_PET_GENDER
        };


        Cursor c = contentResolver.query(PetContact.PetEntry.CONTENT_URI, projection, null, null, null);

        return c;
    }

    @Override
    public void deleteAllItemWithContentResolver(ContentResolver contentResolver) {

        contentResolver.delete(PetContact.PetEntry.CONTENT_URI, null, null);
    }
}


