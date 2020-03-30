package com.waoss.ciby.apis;

import android.location.Location;

import java.util.List;

public interface Consumer {
    interface External {
        Location getLocation();
    }

    interface Internal extends User {
        List<Order> getOrders(Producer producer);

        List<Order> getOrders();

        List<Producer> getNearbyProducers();
    }
}
