package com.example.alexandru.petsapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alexandru.adapter.PetCursorAdapter;
import com.example.alexandru.dao.PetsDao;
import com.example.alexandru.dao.PetsDaoImpl;
import com.example.alexandru.data.PetContact;
import com.example.alexandru.model.Pet;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    // These are the Contacts rows that we will retrieve
    private static final String[] PROJECTION = null;
    // This is the select criteria
    private static final String SELECTION = null;

    private final int LOADER_INDEX = 0;
    private PetCursorAdapter petCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });


        ListView lvPets = (ListView) findViewById(R.id.list_pets);

        View emptyView = findViewById(R.id.empty_view);
        lvPets.setEmptyView(emptyView);

        petCursorAdapter = new PetCursorAdapter(this, null);
        lvPets.setAdapter(petCursorAdapter);

        lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                //Uri editUri = PetContact.PetEntry.CONTENT_URI;
                Uri editUri = Uri.withAppendedPath(PetContact.PetEntry.CONTENT_URI, id + "");
                intent.setData(editUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(LOADER_INDEX, null, this);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data: {
                insetPet();
                return true;
            }
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries: {
                deleteAllPets();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPets() {

        PetsDao<Pet> petsDao = new PetsDaoImpl();
        petsDao.deleteAllItemWithContentResolver(getContentResolver());

    }

    private void insetPet() {

        Pet tempPet = new Pet();


        tempPet.setWeight(12 + (int) (Math.random() * 200));
        tempPet.setGender(1);
        tempPet.setBreed("Terrier");
        tempPet.setName("Toto");

        PetsDao<Pet> petsDao = new PetsDaoImpl();
        petsDao.insertItemAndGetIdWithContentResolver(getContentResolver(), tempPet);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // PetsDao<Pet> petsDao = new PetsDaoImpl();
        // Cursor res = petsDao.getAllItemsContentResolver(getContentResolver());

        // petCursorAdapter  = new PetCursorAdapter(this, res);


        return new CursorLoader(this, PetContact.PetEntry.CONTENT_URI, PROJECTION, SELECTION, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        petCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        petCursorAdapter.swapCursor(null);
    }
}
