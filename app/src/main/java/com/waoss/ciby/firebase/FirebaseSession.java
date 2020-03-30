package com.waoss.ciby.firebase;

import com.waoss.ciby.apis.Consumer;
import com.waoss.ciby.apis.Producer;
import com.waoss.ciby.apis.Session;
import com.waoss.ciby.apis.UserCredentials;
import okhttp3.*;

import java.io.IOException;

public class FirebaseSession implements Session {

    private OkHttpClient httpClient;
    private boolean registered;

    public FirebaseSession(boolean registered) {
        this.registered = registered;
    }

    @Override
    public Consumer.Internal consumerLogin(UserCredentials credentials) {
        return new FirebaseConsumer.Internal(this, credentials.getUsername());
    }

    @Override
    public Producer.Internal producerLogin(UserCredentials credentials) {
        return null;
    }

    public void writeData(String url, String json) {
        final Request request = new Request.Builder()
                .url("https://ciby-972dd.firebaseio.com/" + url + "/")
                .post(RequestBody.create(json, MediaType.get("application-json")))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData(String url, Headers headers) {
        final Request request = new Request.Builder()
                .url("https://ciby-972dd.firebaseio.com/" + url + "/")
                .headers(headers)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return String.valueOf(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
