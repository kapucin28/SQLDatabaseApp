package com.kapucin28.sqldbapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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
