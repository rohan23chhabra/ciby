package com.waoss.ciby.utils;

import com.google.android.gms.maps.model.LatLng;
import com.waoss.ciby.apis.UserCredentials;

public class PojoUserCredentials implements UserCredentials {

    private String username;
    private LatLng location;

    public PojoUserCredentials(String username, LatLng location) {
        this.username = username;
        this.location = location;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public LatLng getLocation() {
        return location;
    }
}
