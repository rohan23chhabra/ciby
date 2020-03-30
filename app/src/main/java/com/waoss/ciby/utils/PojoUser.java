package com.waoss.ciby.utils;

import android.location.Location;
import com.waoss.ciby.apis.User;

public class PojoUser implements User {

    private String username;

    public PojoUser(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Location getLocation() {
        return null;
    }
}
