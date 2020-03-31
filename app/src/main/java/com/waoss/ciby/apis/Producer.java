package com.waoss.ciby.apis;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface Producer {
    interface External {
        LatLng getLocation();
        String getPhoneNumber();
        List<Item> getItemList();
    }
    interface Internal extends User {
        void addItem(Item item);
        Item getItem(String name);
        void removeItem(Item item);
    }
}
