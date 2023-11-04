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

public class ActivityUpdate extends AppCompatActivity {
    private EditText updateAddressEditText;
    private EditText updateLatitudeEditText;
    private EditText updateLongitudeEditText;
    private Button updateLocationButton;
    private Button button;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize UI elements
        updateAddressEditText = findViewById(R.id.updateAddressEditText);
        updateLatitudeEditText = findViewById(R.id.updateLatitudeEditText);
        updateLongitudeEditText = findViewById(R.id.updateLongitudeEditText);
        updateLocationButton = findViewById(R.id.updateLocationButton);
        databaseHelper = new LocationDatabaseHelper(this);

        button = findViewById(R.id.button);
        button.setBackgroundColor(Color.RED); // Set the button background color to red.

        // Set a click listener for the updateLocationButton
        updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });
    }

    // Method to handle the updateLocationButton click event
    private void updateLocation() {
        String address = updateAddressEditText.getText().toString();
        String newLatitudeText = updateLatitudeEditText.getText().toString();
        String newLongitudeText = updateLongitudeEditText.getText().toString();

        if (address.isEmpty() || newLatitudeText.isEmpty() || newLongitudeText.isEmpty()) {
            // Handle the case when any field is empty
            showToast("Please fill in all fields");
            return;
        }

        try {
            double newLatitude = Double.parseDouble(newLatitudeText);
            double newLongitude = Double.parseDouble(newLongitudeText);

            // Query the location from the database based on the provided address
            Location location = databaseHelper.queryLocationByAddress(address);

            if (location != null) {
                // Check if the new location data is the same as the existing one
                if (newLatitude == location.getLatitude() && newLongitude == location.getLongitude()) {
                    showToast("No changes to update");
                    return;
                }

                // Update the location in the database
                databaseHelper.updateLocation(location.getAddress(), newLatitude, newLongitude);
                showToast("Location updated successfully");
                finish(); // Return to the previous activity
            } else {
                // Handle the case when the address is not found in the database
                showToast("Address not found in the database");
            }
        } catch (NumberFormatException e) {
            // Handle the case when latitude or longitude values are not valid numbers
            showToast("Invalid latitude or longitude format");
        }
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
