package com.example.alexandru.dao;

import com.example.alexandru.data.PetDbHelper;

import java.util.List;

/**
 * Created by Alexandru on 5/12/2017.
 */

public interface DbBasicOperations<E> {

    long insertItemAndGetId(PetDbHelper mDbHelper, E item);

    void deleteItem(PetDbHelper mDbHelper, long id);

    void updateItem(PetDbHelper mDbHelper, long id, E item);

    E getItem(PetDbHelper mDbHelper, long id);

    List<E> getAllItems(PetDbHelper mDbHelper);

}
