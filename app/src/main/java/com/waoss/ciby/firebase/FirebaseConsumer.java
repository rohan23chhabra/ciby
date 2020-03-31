package com.waoss.ciby.firebase;

import com.google.android.gms.maps.model.LatLng;
import com.waoss.ciby.apis.Consumer;
import com.waoss.ciby.apis.UserCredentials;
import com.waoss.ciby.utils.PojoProducer;

import java.util.List;

public class FirebaseConsumer implements Consumer {

    public static class Internal implements Consumer.Internal {

        private final FirebaseSession session;
        private UserCredentials userCredentials;

        public Internal(FirebaseSession session, UserCredentials userCredentials) {
            this.session = session;
            this.userCredentials = userCredentials;
        }

        @Override
        public List<PojoProducer> getNearbyProducers() {
            // TODO : Implement this

            return session.getNearbyProducers(getLocation());
        }

        @Override
        public String getUsername() {
            return userCredentials.getUsername();
        }

        @Override
        public LatLng getLocation() {
            return userCredentials.getLocation();
        }
    }
}
