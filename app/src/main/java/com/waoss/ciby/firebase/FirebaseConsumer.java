package com.waoss.ciby.firebase;

import android.location.Location;
import com.waoss.ciby.apis.Consumer;
import com.waoss.ciby.apis.Order;
import com.waoss.ciby.apis.Producer;

import java.util.List;

public class FirebaseConsumer implements Consumer {

    public static class Internal implements Consumer.Internal {

        private final FirebaseSession session;
        private String username;

        public Internal(FirebaseSession session, String username) {
            this.session = session;
            this.username = username;
        }

        @Override
        public List<Order> getOrders(Producer producer) {
            return null;
        }

        @Override
        public List<Order> getOrders() {
            return null;
        }

        @Override
        public List<Producer> getNearbyProducers() {
            return null;
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

    public static class External implements Consumer.External {

        @Override
        public Location getLocation() {
            return null;
        }

        @Override
        public String getPhoneNumber() {
            return null;
        }
    }
}
