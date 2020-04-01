package com.waoss.ciby.utils;

import com.waoss.ciby.apis.Zone;

public class PojoZone implements Zone {

    private String city;
    private String latitude;
    private String longitude;
    private String email;
    private String hospitalLandline;
    private String policeLandline;
    private String hospitalName;

    public PojoZone(String city, String latitude, String longitude, String email, String hospitalLandline, String policeLandline, String hospitalName) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.hospitalLandline = hospitalLandline;
        this.policeLandline = policeLandline;
        this.hospitalName = hospitalName;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getHospitalLandline() {
        return hospitalLandline;
    }

    @Override
    public String getPoliceLandline() {
        return policeLandline;
    }

    @Override
    public String getHospitalName() {
        return hospitalName;
    }
}
