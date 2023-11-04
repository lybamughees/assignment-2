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

public class ActivityDelete extends AppCompatActivity {
    private EditText deleteAddressEditText;
    private Button deleteLocationButton;
    private Button button;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize UI elements
        deleteAddressEditText = findViewById(R.id.deleteAddressEditText);
        deleteLocationButton = findViewById(R.id.deleteLocationButton);
        databaseHelper = new LocationDatabaseHelper(this);
        button = findViewById(R.id.button);
        button.setBackgroundColor(Color.RED);

        deleteLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLocation();
            }
        });
    }

    // Method to delete a location based on the provided address
    private void deleteLocation() {
        String address = deleteAddressEditText.getText().toString();

        if (address.isEmpty()) {
            // Handle the case when the address field is empty
            showToast("Please enter an address to delete");
            return;
        }

        // Query the database to find the location with the specified address
        Location location = databaseHelper.queryLocationByAddress(address);

        if (location != null) {
            // Delete the location from the database
            databaseHelper.deleteLocation(location.getAddress());
            showToast("Location deleted successfully");
        } else {
            // Handle the case when the address is not found in the database
            showToast("Address not found in the database");
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
