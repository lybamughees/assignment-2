package com.example.locationpinned;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class LocationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL);";

    public LocationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // Create the 'location' table when the database is first created.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Drop the table if it exists during an upgrade.
        onCreate(db); // Recreate the table after dropping it.
    }

    // Method to add a list of Location objects to the database.
    public void addLocations(List<Location> locations) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Location location : locations) {
            values.put(COLUMN_ADDRESS, location.getAddress());
            values.put(COLUMN_LATITUDE, location.getLatitude());
            values.put(COLUMN_LONGITUDE, location.getLongitude());

            db.insert(TABLE_NAME, null, values); // Insert a new location into the table.
        }

        db.close();
    }

    // Method to retrieve all locations from the database and return them as a list.
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(cursor.getInt(0));
                location.setAddress(cursor.getString(1));
                location.setLatitude(cursor.getDouble(2));
                location.setLongitude(cursor.getDouble(3));

                locations.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return locations;
    }

    // Method to query a location by its address from the database.
    public Location queryLocationByAddress(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Location location = null;

        Cursor cursor = db.query(
                TABLE_NAME,                        // Table name
                new String[] {COLUMN_LATITUDE, COLUMN_LONGITUDE}, // Columns to retrieve
                COLUMN_ADDRESS + "=?",             // WHERE clause
                new String[] {address},            // WHERE arguments
                null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            location = new Location(address, latitude, longitude);
        }

        if (cursor != null) {
            cursor.close();
        }

        return location;
    }

    // Method to update the latitude and longitude of a location based on its address.
    public void updateLocation(String address, double newLatitude, double newLongitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LATITUDE, newLatitude);
        values.put(COLUMN_LONGITUDE, newLongitude);

        db.update(TABLE_NAME, values, COLUMN_ADDRESS + " = ?", new String[]{address});
        db.close();
    }

    // Method to delete a location from the database based on its address.
    public void deleteLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_ADDRESS + " = ?", new String[]{address});
        db.close();
    }
}
