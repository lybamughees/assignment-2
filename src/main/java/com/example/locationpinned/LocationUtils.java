package com.example.locationpinned;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;

import java.util.Locale;



public class LocationUtils {

    // Method to read location data from a JSON file and obtain addresses using geocoding.
    public static List<Location> readAllLocations(Context context) {
        List<Location> locations = new ArrayList<>();
        Resources resources = context.getResources();

        try {
            // Read the JSON file from the res/raw directory
            InputStream inputStream = resources.openRawResource(R.raw.coordinates);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonContent = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");

                // Use Geocoding to find the address
                String address = getAddressFromCoordinates(context, latitude, longitude);
                Log.d("LocationUtils", "Latitude: " + latitude + ", Longitude: " + longitude + ", Address: "+ address);

                // Create a Location object and add it to the list
                Location location = new Location(address, latitude, longitude);
                // Log the address to check if it's valid
                Log.d("Address?", address);

                locations.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LocationUtils", "Error reading JSON file: " + e.getMessage());
        }

        return locations;
    }

    // Method to obtain an address from coordinates using geocoding.
    private static String getAddressFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String address = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);

                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    addressBuilder.append(returnedAddress.getAddressLine(i));
                    if (i < returnedAddress.getMaxAddressLineIndex()) {
                        addressBuilder.append(", ");
                    }
                }

                address = addressBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }
}
