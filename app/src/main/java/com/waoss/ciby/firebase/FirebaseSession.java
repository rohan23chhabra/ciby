package com.waoss.ciby.firebase;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;
import com.waoss.ciby.apis.*;
import com.waoss.ciby.utils.PojoItem;
import com.waoss.ciby.utils.PojoProducer;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.waoss.ciby.utils.CoronaUtils.GSON;

public class FirebaseSession implements Session {

    private OkHttpClient httpClient;
    private boolean registered;
    private UserType userType;
    public static final String FIREBASE_HOST = "https://ciby-972dd.firebaseio.com/";

    {
        httpClient = new OkHttpClient();
    }

    public FirebaseSession(boolean registered) {
        this.registered = registered;
    }

    public FirebaseSession(UserType userType) {
        this.userType = userType;
    }

    public FirebaseSession() {

    }

    @Override
    public Consumer.Internal consumerLogin(UserCredentials credentials) {
        return new FirebaseConsumer.Internal(this, credentials);
    }

    @Override
    public Producer.Internal producerLogin(UserCredentials credentials) {
        return null;
    }

    public void writeData(String url, String json) {
        final Request request = new Request.Builder()
                .url(FIREBASE_HOST + url + ".json")
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
                .url(FIREBASE_HOST + url + ".json" + queryParameters)
                .build();
        String result = "nullda";
        final CallbackFuture callbackFuture = new CallbackFuture();
        httpClient.newCall(request).enqueue(callbackFuture);
        Response response = null;
        try {
            response = callbackFuture.get();
            result = Objects.requireNonNull(response.body()).string();
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean login(UserCredentials userCredentials) {
        return (readData("users/" + userCredentials.getUsername(), "") != null);
    }

    public void addItem(Item item) {
        String itemJson = GSON.toJson(item);
        writeData("items/" + item.getName(), itemJson);
    }

    public Item getItem(String name) {
        String json = readData("items/" + name, "");
        PojoItem pojoItem = GSON.fromJson(json, PojoItem.class);
        return pojoItem;
    }

    public void removeItem(Item item) {
        removeData("items/" + item.getName());
    }

    private void removeData(String url) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        final CallbackFuture callbackFuture = new CallbackFuture();
        httpClient.newCall(request).enqueue(callbackFuture);
        Response response = null;
        try {
            response = callbackFuture.get();
            Log.d("remove-data", response.toString());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<PojoProducer> getNearbyProducers(LatLng consumerLocation) {
        Type listType = new TypeToken<List<PojoProducer>>() {}.getType();
        return GSON.fromJson(readData("producers", ""), listType);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static class CallbackFuture extends CompletableFuture<Response> implements Callback {

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
