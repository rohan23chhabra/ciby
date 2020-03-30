package com.waoss.ciby.utils;

import com.waoss.ciby.apis.UserCredentials;

public class PojoUserCredentials implements UserCredentials {

    private String username;
    private String password;

    public PojoUserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
