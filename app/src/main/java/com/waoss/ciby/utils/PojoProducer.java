package com.waoss.ciby.utils;

import com.google.android.gms.maps.model.LatLng;
import com.waoss.ciby.apis.Item;
import com.waoss.ciby.apis.Producer;

import java.util.List;

public class PojoProducer implements Producer.External {

    private LatLng location;
    private String phoneNumber;
    private List<Item> itemList;

    public PojoProducer(LatLng location, String phoneNumber, List<Item> itemList) {
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.itemList = itemList;
    }

    @Override
    public LatLng getLocation() {
        return location;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public List<Item> getItemList() {
        return itemList;
    }
}
