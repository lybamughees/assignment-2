package com.example.locationpinned;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LocationListActivity extends AppCompatActivity {
    private ListView locationListView;
    private LocationAdapter adapter;
    private List<Location> locations;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item); // Set the activity layout to 'list_item'.

        // Initialize views and data sources.
        locationListView = findViewById(R.id.locationListView); // Get a reference to the ListView.
        locations = loadLocationsFromDatabase(); // Load locations from the database.
        adapter = new LocationAdapter(this, locations); // Create an adapter for the ListView.
        locationListView.setAdapter(adapter); // Set the adapter to display location data.
    }

    // Method to load locations from the database.
    private List<Location> loadLocationsFromDatabase() {
        LocationDatabaseHelper databaseHelper = new LocationDatabaseHelper(this);
        return databaseHelper.getAllLocations(); // Retrieve a list of locations from the database.
    }

    // Method to handle the "Cancel" button click event.
    public void cancel(View view) {
        // Create an intent to navigate back to the MainActivity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); // Start the MainActivity using the intent.
    }
}
