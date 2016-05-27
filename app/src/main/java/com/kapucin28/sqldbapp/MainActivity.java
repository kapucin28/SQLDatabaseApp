package com.kapucin28.sqldbapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // SQL variables--------------------------------------------------------------------------------
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase = null;
    private String databaseName = "SQL DB";
    private String tableName = "sql";
    private String name, email, phone, id, personsList;
    private int nameColumn, emailColumn, phoneColumn, idColumn;
    private File file;
    //----------------------------------------------------------------------------------------------

    // Database variables---------------------------------------------------------------------------
    private List<String> list = new ArrayList<>();
    private String personName, personEmail, personPhone, personID;
    //----------------------------------------------------------------------------------------------

    // TextViews variables--------------------------------------------------------------------------
    private EditText textName, textEmail, textPhone, textID;
    private TextView textDB;
    //----------------------------------------------------------------------------------------------

    // MenuItems state variables--------------------------------------------------------------------
    private boolean createMenuItem = true;
    private boolean deleteMenuItem = false;
    private boolean clearMenuItem = false;
    private boolean displayMenuItem = false;
    private boolean addMenuItem = false;
    private boolean removeMenuItem = false;
    //----------------------------------------------------------------------------------------------

    // OnCreate method------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = (EditText) findViewById(R.id.person_name_edit_text);
        textEmail = (EditText) findViewById(R.id.email_edit_text);
        textPhone = (EditText) findViewById(R.id.phone_edit_text);
        textID = (EditText) findViewById(R.id.ID_edit_text);
        textDB = (TextView) findViewById(R.id.display_DB);
    }
    //----------------------------------------------------------------------------------------------

    // MenuItems initial status method--------------------------------------------------------------
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(1).setEnabled(createMenuItem);
        menu.getItem(2).setEnabled(deleteMenuItem);
        menu.getItem(3).setEnabled(clearMenuItem);
        menu.getItem(4).setEnabled(displayMenuItem);
        menu.getItem(5).setEnabled(addMenuItem);
        menu.getItem(6).setEnabled(removeMenuItem);
        return super.onPrepareOptionsMenu(menu);
    }
    //----------------------------------------------------------------------------------------------

    // Menu status method---------------------------------------------------------------------------
    private void menuStatus(boolean createMenuItem, boolean deleteMenuItem, boolean clearMenuItem,
                            boolean displayMenuItem, boolean addMenuItem, boolean removeMenuItem) {
        this.createMenuItem = createMenuItem;
        this.deleteMenuItem = deleteMenuItem;
        this.clearMenuItem = clearMenuItem;
        this.displayMenuItem = displayMenuItem;
        this.addMenuItem = addMenuItem;
        this.removeMenuItem = removeMenuItem;
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
            case R.id.create_database:
                createDB();
                break;
            case R.id.delete_database:
                deleteDB();
                break;
            case R.id.clear_DB:
                clearDB();
                break;
            case R.id.display_DB:
                break;
            case R.id.add_person:
                break;
            case R.id.remove_person:
                break;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------

    // Creating database method---------------------------------------------------------------------
    private void createDB() {

        // Creating SQL DB--------------------------------------------------------------------------
        sqLiteDatabase = this.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                " (id integer primary key, name VARCHAR, email VARCHAR, phone long);");
        Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
        //------------------------------------------------------------------------------------------

        // Updating menu status---------------------------------------------------------------------
        menuStatus(false, true, true, true, true, true);
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------

    // Delete database method-----------------------------------------------------------------------
    private void deleteDB() {

        // Deleting SQL DB--------------------------------------------------------------------------
        for (int i = 0; i < list.size() + 1000; i++) {
            personID = String.valueOf(i);
            sqLiteDatabase.execSQL("DELETE FROM " + tableName + " WHERE id= " + personID + ";");
        }
        this.deleteDatabase(databaseName);
        list.clear();
        textDB.setText("");
        Toast.makeText(this, "DB Deleted", Toast.LENGTH_SHORT).show();
        //------------------------------------------------------------------------------------------

        // Updating menu status---------------------------------------------------------------------
        menuStatus(true, false, false, false, false, false);
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------

    // Clear database method------------------------------------------------------------------------
    private void clearDB() {
        if (!list.isEmpty()) {
            list.clear();
            for (int i = 0; i < list.size() + 1000; i++) {
                personID = String.valueOf(i);
                sqLiteDatabase.execSQL("DELETE FROM " + tableName + " WHERE id = " + personID + ";");
            }
            textDB.setText("");
            Toast.makeText(this, "DB Cleared", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "DB Already Empty", Toast.LENGTH_SHORT).show();
        }
    }
    //----------------------------------------------------------------------------------------------
}
