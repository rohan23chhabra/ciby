package com.waoss.ciby.firebase;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.waoss.ciby.apis.Consumer;
import com.waoss.ciby.apis.Producer;
import com.waoss.ciby.apis.Session;
import com.waoss.ciby.apis.UserCredentials;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirebaseSession implements Session {

    private OkHttpClient httpClient;
    private boolean registered;

    public FirebaseSession(boolean registered) {
        this.registered = registered;
        this.httpClient = new OkHttpClient();
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
                .url("https://ciby-972dd.firebaseio.com/" + url + ".json")
                .post(RequestBody.create(json, MediaType.get("application/json")))
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("rest-api", "nigga workin + vibin " + response);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String readData(String url, String queryParameters) {
        final Request request = new Request.Builder()
                .url("https://ciby-972dd.firebaseio.com/" + url + ".json" + queryParameters)
                .build();
        String result = "nullda";
        final CallbackFuture callbackFuture = new CallbackFuture();
        httpClient.newCall(request).enqueue(callbackFuture);
        Response response = null;
        try {
            response = callbackFuture.get();
            result = response.body().string();
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean login(UserCredentials userCredentials) {
        return (readData("users/" + userCredentials.getUsername(), "") != null);
    }

    public void placeOrder(Consumer.External consumer, Producer.External producer) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public class CallbackFuture extends CompletableFuture<Response> implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            super.completeExceptionally(e);
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            super.complete(response);
        }
    }
}
