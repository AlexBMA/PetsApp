package com.example.alexandru.petsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alexandru.dao.DbBasicOperations;
import com.example.alexandru.dao.PetsDaoImpl;
import com.example.alexandru.data.PetDbHelper;
import com.example.alexandru.model.Pet;

import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;




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

        mDbHelper = new PetDbHelper(this);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {


        DbBasicOperations<Pet> dbOp = new PetsDaoImpl();
        List<Pet> list = dbOp.getAllItems(mDbHelper);

        TextView textView = (TextView) findViewById(R.id.text_view_pet);

        int size = list.size();
        for (int i = 0; i < size; i++) {


            textView.append("\n" + list.get(i).toString());
        }


        //dbOp.getItem(mDbHelper,1);
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
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void insetPet() {

        Pet tempPet = new Pet();
        tempPet.setBreed("Terrier");
        tempPet.setName("Toto");
        tempPet.setWeight(12);
        tempPet.setGender(1);


        DbBasicOperations<Pet> petsOP = new PetsDaoImpl();
        PetDbHelper mDbHelper = new PetDbHelper(this);
        long newRowId = petsOP.insertItemAndGetId(mDbHelper, tempPet);

        Log.e("New row id ", newRowId + " ");

    }


}
