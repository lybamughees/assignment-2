package com.example.locationpinned;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class ActivityQuery extends AppCompatActivity {
    private EditText addressEditText;
    private Button queryButton;
    private Button button;
    private TextView resultTextView;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addressEditText = findViewById(R.id.addressEditText);
        queryButton = findViewById(R.id.queryButton);
        resultTextView = findViewById(R.id.resultTextView);

        button = findViewById(R.id.button);
        button.setBackgroundColor(Color.RED);

        // Initialize the database helper
        databaseHelper = new LocationDatabaseHelper(this);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the query address from the input field
                String queryAddress = addressEditText.getText().toString();
                Location location = databaseHelper.queryLocationByAddress(queryAddress);

                if (location != null) {
                    // Display the latitude and longitude of the found location
                    resultTextView.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
                } else {
                    // Handle the case when the address is not found in the database
                    resultTextView.setText("Address not found in the database.");
                }
            }
        });
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
