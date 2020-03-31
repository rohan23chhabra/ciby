package com.waoss.ciby.apis;

import com.google.android.gms.maps.model.LatLng;

public interface UserCredentials {
    String getUsername();
    LatLng getLocation();
}
