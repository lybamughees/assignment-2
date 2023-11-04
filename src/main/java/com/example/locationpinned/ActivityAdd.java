package com.example.locationpinned;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class ActivityAdd extends AppCompatActivity {
    private EditText addAddressEditText;
    private EditText addLatitudeEditText;
    private EditText addLongitudeEditText;
    private Button addLocationButton;
    private Button button;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize UI elements
        addAddressEditText = findViewById(R.id.addAddressEditText);
        addLatitudeEditText = findViewById(R.id.addLatitudeEditText);
        addLongitudeEditText = findViewById(R.id.addLongitudeEditText);
        addLocationButton = findViewById(R.id.addLocationButton);
        databaseHelper = new LocationDatabaseHelper(this);

        button = findViewById(R.id.button);
        button.setBackgroundColor(Color.RED);

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });
    }

    // Method to add a new location to the database
    private void addLocation() {
        String address = addAddressEditText.getText().toString();
        String latitudeText = addLatitudeEditText.getText().toString();
        String longitudeText = addLongitudeEditText.getText().toString();

        if (address.isEmpty() || latitudeText.isEmpty() || longitudeText.isEmpty()) {
            // Handle the case when any field is empty
            showToast("Please fill in all fields");
            return;
        }

        try {
            double latitude = Double.parseDouble(latitudeText);
            double longitude = Double.parseDouble(longitudeText);

            // Check if the address already exists in the database
            if (isAddressExists(address)) {
                showToast("Address already exists in the database");
                return;
            }

            Location newLocation = new Location(address, latitude, longitude);
            List<Location> locations = new ArrayList<>();
            locations.add(newLocation);

            databaseHelper.addLocations(locations); // Add the new location to the database

            showToast("Location added successfully");
            finish(); // Return to the previous activity
        } catch (NumberFormatException e) {
            // Handle the case when latitude or longitude values are not valid numbers
            showToast("Invalid latitude or longitude format");
        }
    }

    // Method to check if the address already exists in the database
    private boolean isAddressExists(String address) {
        Location existingLocation = databaseHelper.queryLocationByAddress(address);
        return existingLocation != null;
    }


    // Method to display a short toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Finish the current activity and return to the previous screen
        return true;
    }

    // Method to handle the "Cancel" button click event
    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
