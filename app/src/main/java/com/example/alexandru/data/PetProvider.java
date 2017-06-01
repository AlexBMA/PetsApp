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
import android.util.Log;

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
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return PetContact.PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return PetContact.PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
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


        String name = values.getAsString(PetContact.PetEntry.COLUMN_PET_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        String breed = values.getAsString(PetContact.PetEntry.COLUMN_PET_BREED);
        if (breed == null) {
            throw new IllegalArgumentException("Pet requires a breed");
        }

        Integer weight = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_WEIGHT);
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("Pet requires a valid weight");
        }

        Integer gender = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_GENDER);
        if (gender == null || !PetContact.PetEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires a valid  gender");
        }


        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long newId = db.insert(PetContact.PetEntry.TABLE_NAME, null, values);

        if (newId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, newId);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:

                int rezDeleteAll = database.delete(PetContact.PetEntry.TABLE_NAME, null, null);
                if (rezDeleteAll != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteAll;

            case PET_ID:
                // Delete a single row given by the ID in the URI
                selection = PetContact.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri, null);
                int rezOfDeleteById = database.delete(PetContact.PetEntry.TABLE_NAME, selection, selectionArgs);
                if (rezOfDeleteById != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezOfDeleteById;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        String name = values.getAsString(PetContact.PetEntry.COLUMN_PET_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        String breed = values.getAsString(PetContact.PetEntry.COLUMN_PET_BREED);
        if (breed == null) {
            throw new IllegalArgumentException("Pet requires a breed");
        }

        Integer weight = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_WEIGHT);
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("Pet requires a valid weight");
        }

        Integer gender = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_GENDER);
        if (gender == null || !PetContact.PetEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires a valid  gender");
        }

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:
                return updatePet(uri, values, selection, selectionArgs);
            case PET_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = PetContact.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }


    }


    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        String name = values.getAsString(PetContact.PetEntry.COLUMN_PET_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        String breed = values.getAsString(PetContact.PetEntry.COLUMN_PET_BREED);
        if (breed == null) {
            throw new IllegalArgumentException("Pet requires a breed");
        }

        Integer weight = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_WEIGHT);
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("Pet requires a valid weight");
        }

        Integer gender = values.getAsInteger(PetContact.PetEntry.COLUMN_PET_GENDER);
        if (gender == null || !PetContact.PetEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires a valid  gender");
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int nrRows = database.update(PetContact.PetEntry.TABLE_NAME, values, selection, selectionArgs);

        if (nrRows != 0) getContext().getContentResolver().notifyChange(uri, null);

        return nrRows;
    }
}
