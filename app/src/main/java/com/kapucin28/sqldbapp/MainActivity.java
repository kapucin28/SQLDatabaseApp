package com.kapucin28.sqldbapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *      This is the main class where all of the actions
 * will be performed
 */

public class MainActivity extends AppCompatActivity {

    // SQL variables & constants--------------------------------------------------------------------
    private SQLiteDatabase sqLiteDatabase = null;
    private final String databaseName = "SQL DB";
    private final String tableName = "sql";
    //----------------------------------------------------------------------------------------------

    // Database variables---------------------------------------------------------------------------
    private List<String> list = new ArrayList<>();
    private String personID;
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
        textDB = (TextView) findViewById(R.id.DB_details_text_view);

        createDBStartup();
        displayStartup();
    }
    //----------------------------------------------------------------------------------------------

    // Saving state method--------------------------------------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("createMenuItem", createMenuItem);
        outState.putBoolean("deleteMenuItem", deleteMenuItem);
        outState.putBoolean("clearMenuItem", clearMenuItem);
        outState.putBoolean("displayMenuItem", displayMenuItem);
        outState.putBoolean("addMenuItem", addMenuItem);
        outState.putBoolean("removeMenuItem", removeMenuItem);
    }
    //----------------------------------------------------------------------------------------------

    // Restoring state method-----------------------------------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        createMenuItem = savedInstanceState.getBoolean("createMenuItem");
        deleteMenuItem = savedInstanceState.getBoolean("deleteMenuItem");
        clearMenuItem = savedInstanceState.getBoolean("clearMenuItem");
        displayMenuItem = savedInstanceState.getBoolean("displayMenuItem");
        addMenuItem = savedInstanceState.getBoolean("addMenuItem");
        removeMenuItem = savedInstanceState.getBoolean("removeMenuItem");
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
                displayDB();
                break;
            case R.id.add_person:
                addPerson();
                break;
            case R.id.remove_person:
                removePerson();
                break;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------

    // Creating / opening database at startup method------------------------------------------------
    private void createDBStartup() {

        // Creating SQL DB--------------------------------------------------------------------------
        sqLiteDatabase = this.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                " (id integer primary key, name VARCHAR, email VARCHAR, phone long);");
        //------------------------------------------------------------------------------------------

        // Updating menu status---------------------------------------------------------------------
        menuStatus(false, true, true, true, true, true);
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------

    // Creating database method---------------------------------------------------------------------
    private void createDB() {
        createDBStartup();
        Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
    }
    //----------------------------------------------------------------------------------------------

    // Delete database method-----------------------------------------------------------------------
    private void deleteDB() {

        // Deleting SQL DB--------------------------------------------------------------------------
        for (int i = 0; i < list.size(); i++) {
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
            for (int i = 0; i < list.size(); i++) {
                personID = String.valueOf(i);
                sqLiteDatabase.execSQL("DELETE FROM " + tableName + " WHERE id = " + personID + ";");
            }
            this.deleteDatabase(databaseName);
            createDBStartup();
            displayStartup();
            textDB.setText("");
            Toast.makeText(this, "DB Cleared", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "DB Already Empty", Toast.LENGTH_SHORT).show();
        }
    }
    //----------------------------------------------------------------------------------------------

    // Display database at startup method-----------------------------------------------------------
    private void displayStartup() {
        // Refresh textView-------------------------------------------------------------------------
        textDB.setText("");
        //------------------------------------------------------------------------------------------

        // Selecting items from database------------------------------------------------------------
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + tableName, null);
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int emailColumn = cursor.getColumnIndex("email");
        int phoneColumn = cursor.getColumnIndex("phone");
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String email = cursor.getString(emailColumn);
                String phone = cursor.getString(phoneColumn);

                String personsDetails = id + ". Name: " + name + "\n" + "     Email: " + email + "\n" + "     Phone: " +
                        phone + "\n" + "\n";
                list.add(personsDetails);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Add Persons To DB", Toast.LENGTH_SHORT).show();
            textDB.setText("");
        }
        //------------------------------------------------------------------------------------------

        // Displaying items from list---------------------------------------------------------------
        for (String person : list) {
            textDB.append(person);
        }
        cursor.close();
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------

    // Display database content method--------------------------------------------------------------
    private void displayDB() {
        list.clear();
        displayStartup();
    }
    //----------------------------------------------------------------------------------------------

    // Adding person to DB method-------------------------------------------------------------------
    private void addPerson() {
        if (!TextUtils.isEmpty(textName.getText()) && !TextUtils.isEmpty(textEmail.getText())
                && !TextUtils.isEmpty(textPhone.getText())) {
            String personName = textName.getText().toString();
            String personEmail = textEmail.getText().toString();
            String personPhone = textPhone.getText().toString();
            sqLiteDatabase.execSQL("INSERT INTO " + tableName + " (name, email, phone) VALUES ('" + personName + "', '" +
                    personEmail + "', '" + personPhone + "');");
            textName.getText().clear();
            textEmail.getText().clear();
            textPhone.getText().clear();
            Toast.makeText(this, "Person Added", Toast.LENGTH_SHORT).show();
            displayDB();
        } else {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
        }
    }
    //----------------------------------------------------------------------------------------------

    // Remove person from DB method-----------------------------------------------------------------
    private void removePerson() {
        if (!TextUtils.isEmpty(textID.getText())) {
            personID = textID.getText().toString();
            sqLiteDatabase.execSQL("DELETE FROM " + tableName + " WHERE id = " + personID + ";");
            textID.getText().clear();
            Toast.makeText(this, "Person Removed", Toast.LENGTH_SHORT).show();
            displayDB();
        } else {
            Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show();
        }
    }
    //----------------------------------------------------------------------------------------------
}
