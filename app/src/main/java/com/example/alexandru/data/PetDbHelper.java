package com.example.alexandru.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Alexandru on 5/10/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;


    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetContact.PetEntry.TABLE_NAME + " ("
                + PetContact.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetContact.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetContact.PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetContact.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetContact.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
