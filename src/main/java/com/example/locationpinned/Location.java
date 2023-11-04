package com.example.locationpinned;

// Define a class named Location to represent a geographic location.
public class Location {
    private int id;         // An identifier for the location.
    private String address;  // The address associated with the location.
    private double latitude; // The latitude coordinate of the location.
    private double longitude; // The longitude coordinate of the location.

    // Default constructor, used for creating instances with no initial data.
    public Location() {
        // Empty constructor for creating instances of Location
    }

    // Constructor with parameters to initialize address, latitude, and longitude.
    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor with parameters to initialize all fields, including id.
    public Location(int id, String address, double latitude, double longitude) {
        this.id = id;             // Initialize the 'id' field with the provided value.
        this.address = address;   // Initialize the 'address' field with the provided value.
        this.latitude = latitude; // Initialize the 'latitude' field with the provided value.
        this.longitude = longitude; // Initialize the 'longitude' field with the provided value.
    }


    // Getter method to retrieve the 'id' of the location.
    public int getId() {
        return id;
    }

    // Setter method to set the 'id' of the location.
    public void setId(int id) {
        this.id = id;
    }

    // Getter method to retrieve the 'address' of the location.
    public String getAddress() {
        return address;
    }

    // Setter method to set the 'address' of the location.
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter method to retrieve the 'latitude' of the location.
    public double getLatitude() {
        return latitude;
    }

    // Setter method to set the 'latitude' of the location.
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getter method to retrieve the 'longitude' of the location.
    public double getLongitude() {
        return longitude;
    }

    // Setter method to set the 'longitude' of the location.
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
