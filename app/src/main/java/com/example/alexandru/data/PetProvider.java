package com.example.alexandru.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alexandru on 5/20/2017.
 */

public class PetProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int PETS = 100;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int PET_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(PetContact.CONTENT_AUTHORITY, PetContact.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContact.CONTENT_AUTHORITY, PetContact.PATH_PETS + "/#", PET_ID);
    }

    private PetDbHelper mDbHelper;

    @Override
    public boolean onCreate() {

        mDbHelper = new PetDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                cursor = database.query(PetContact.PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PET_ID:
                selection = PetContact.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PetContact.PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }


        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:
                return insertPets(uri, values);

            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }

    }

    private Uri insertPets(Uri uri, ContentValues values) {


        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long newId = db.insert(PetContact.PetEntry.TABLE_NAME, null, values);

        return ContentUris.withAppendedId(uri, newId);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();


        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                database.delete(PetContact.PetEntry.TABLE_NAME, null, null);
                break;
            case PET_ID:
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
