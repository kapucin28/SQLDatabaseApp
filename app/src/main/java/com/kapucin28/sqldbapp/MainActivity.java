package com.kapucin28.sqldbapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // SQL variables--------------------------------------------------------------------------------
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase = null;
    private String databaseName = "SQL DB";
    private String name, email, phone, id, personsList;
    private int nameColumn, emailColumn, phoneColumn, idColumn;
    private File file;
    //----------------------------------------------------------------------------------------------

    // OnCreate method------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //----------------------------------------------------------------------------------------------

    // Creating menu method-------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //----------------------------------------------------------------------------------------------

    // Selected menu items actions method-----------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.exit_app:
                finish();
                break;
            case R.id.create_database:
                break;
            case R.id.delete_database:
                break;
            case R.id.display_DB:
                break;
            case R.id.clear_DB:
                break;
            case R.id.add_person:
                break;
            case R.id.remove_person:
                break;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------
}
