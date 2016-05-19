package com.kapucin28.sqldbapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

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
}
