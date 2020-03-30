package com.waoss.ciby.apis;

import android.location.Location;

import java.util.List;

public interface Producer {
    interface External {
        Location getLocation();
        String getPhoneNumber();
    }
    interface Internal extends User {
        List<Order> getOrders(Consumer consumer);
        List<Order> getOrders();
        Location getDeliveryLocation();
    }
}
