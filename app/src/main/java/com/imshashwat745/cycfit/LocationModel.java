package com.imshashwat745.cycfit;

public class LocationModel {
    private String locationName;
    private int availableBikes;

    public LocationModel(String locationName, int availableBikes) {
        this.locationName = locationName;
        this.availableBikes = availableBikes;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }
}
