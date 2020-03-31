package com.waoss.ciby.firebase;

import com.google.android.gms.maps.model.LatLng;
import com.waoss.ciby.apis.Item;
import com.waoss.ciby.apis.Producer;
import com.waoss.ciby.apis.UserCredentials;

public class FirebaseProducer implements Producer {
    public static class Internal implements Producer.Internal {

        FirebaseSession firebaseSession;
        UserCredentials userCredentials;

        public Internal(FirebaseSession firebaseSession, UserCredentials userCredentials) {
            this.firebaseSession = firebaseSession;
            this.userCredentials = userCredentials;
        }

        @Override
        public void addItem(Item item) {
            firebaseSession.addItem(item);
        }

        @Override
        public Item getItem(String name) {
            return firebaseSession.getItem(name);
        }

        @Override
        public void removeItem(Item item) {
            firebaseSession.removeItem(item);
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
