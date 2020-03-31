package com.waoss.ciby.apis;

import android.location.Location;

import java.util.List;

public interface Consumer {
//    interface External {
//        Location getLocation();
//        String getPhoneNumber();
//    }

    interface Internal extends User {
        List<? extends Producer.External> getNearbyProducers();
    }
}
