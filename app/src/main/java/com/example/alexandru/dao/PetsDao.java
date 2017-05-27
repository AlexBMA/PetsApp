package com.example.alexandru.dao;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * Created by Alexandru on 5/12/2017.
 */

public interface PetsDao<E> extends DbBasicOperations<E> {

    long insertItemAndGetIdWithContentResolver(ContentResolver contentResolver, E item);


    void deleteItemWithContentResolver(ContentResolver contentResolver, long id);

    void updateItemWithContentResolver(ContentResolver contentResolver, long id, E item);

    E getItemWithContentResolver(ContentResolver contentResolver, long id);

    Cursor getAllItemsContentResolver(ContentResolver contentResolver);

}
