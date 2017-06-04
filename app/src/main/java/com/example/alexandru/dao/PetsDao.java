package com.example.alexandru.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Alexandru on 5/12/2017.
 */

public interface PetsDao<E> extends DbBasicOperations<E> {

    long insertItemAndGetIdWithContentResolver(ContentResolver contentResolver, E item);


    void deleteItemWithContentResolver(ContentResolver contentResolver, long id, Uri deleteUri);

    void updateItemWithContentResolver(ContentResolver contentResolver, long id, E item);

    Cursor getItemWithContentResolver(ContentResolver contentResolver, long id);

    Cursor getAllItemsContentResolver(ContentResolver contentResolver);

    void deleteAllItemWithContentResolver(ContentResolver contentResolver);

}
