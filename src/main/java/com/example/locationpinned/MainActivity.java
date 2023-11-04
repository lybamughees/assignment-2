package com.example.locationpinned;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button queryButton;
    private Button addButton;
    private Button deleteButton;
    private Button updateButton;
    private Button viewButton;
    LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Call the method to add all locations to the database
        addAllLocationsToDatabase();

        // Initialize buttons
        queryButton = findViewById(R.id.queryButton);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        viewButton = findViewById(R.id.viewButton);

        // Set click listeners for each button
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Addresses View
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the QueryLocationActivity
                Intent intent = new Intent(MainActivity.this, ActivityQuery.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the AddLocationActivity
                Intent intent = new Intent(MainActivity.this, ActivityAdd.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the DeleteLocationActivity
                Intent intent = new Intent(MainActivity.this, ActivityDelete.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the UpdateLocationActivity
                Intent intent = new Intent(MainActivity.this, ActivityUpdate.class);
                startActivity(intent);
            }
        });
    }

    private void addAllLocationsToDatabase() {
        // Get all locations from the JSON file
        List<Location> allLocations = LocationUtils.readAllLocations(this);

        // Add all locations to the database
        LocationDatabaseHelper databaseHelper = new LocationDatabaseHelper(this);
        databaseHelper.addLocations(allLocations);
    }
}
