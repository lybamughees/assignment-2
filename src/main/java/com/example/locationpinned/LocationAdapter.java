package com.example.locationpinned;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {
    private Context context;

    // Constructor for the LocationAdapter class.
    // It takes a context and a list of Location objects as parameters.
    public LocationAdapter(Context context, List<Location> locations) {
        super(context, 0, locations); // Call the parent class constructor.
        this.context = context; // Store the context for later use.

    }

    // Override the getView method to customize how each item in the list is displayed.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if a recycled view (convertView) is available; if not, inflate a new one.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the Location object associated with the current position in the list.
        Location location = getItem(position);


        // Find TextViews in the inflated view to display address, latitude, and longitude.
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);
        TextView latitudeTextView = convertView.findViewById(R.id.latitudeTextView);
        TextView longitudeTextView = convertView.findViewById(R.id.longitudeTextView);


        // Set the text for each TextView based on the Location object's data.
        addressTextView.setText("Address: " + location.getAddress());
        Log.d("AddressSubmitted: ", location.getAddress());
        latitudeTextView.setText("Latitude: " + location.getLatitude());
        longitudeTextView.setText("Longitude: " + location.getLongitude());

        // Return the customized view to be displayed in the list.
        return convertView;
    }
}
