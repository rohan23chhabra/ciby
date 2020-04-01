package com.waoss.ciby;

import android.content.Intent;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.waoss.ciby.apis.Consumer;
import com.waoss.ciby.apis.Producer;
import com.waoss.ciby.utils.CoronaUtils;
import com.waoss.ciby.utils.PojoProducer;
import com.waoss.ciby.utils.PojoUserCredentials;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.waoss.ciby.utils.CoronaUtils.*;

public class ConsumerActivity extends AppCompatActivity {

    ListView listView;

    private LatLng location;
    private String username;
    private Consumer.Internal consumerInternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        init();
        initUI();
    }

    private void initUI() {
        List<? extends Producer.External> producers = consumerInternal.getNearbyProducers();
        Log.d("prodo", producers.toString());

        List<String> phoneNumbers = producers.stream().map(producer -> {
            String address = getAddress(producer.getLocation());
            String phoneNumber = producer.getPhoneNumber();
            return phoneNumber + "\n" + address;
        }).collect(Collectors.toList());

        listView = findViewById(R.id.producer_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_producer, R.id.producer_phone_number, phoneNumbers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String phoneNumber = (String) parent.getItemAtPosition(position);
            Log.d("click-click", phoneNumber);
            showDetailedProducerActivity(phoneNumber);
        });
    }

    private String getAddress(LatLng location) {
        try {
            return new Geocoder(this, Locale.getDefault())
                    .getFromLocation(location.latitude, location.longitude, 1)
                    .get(0)
                    .getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "An error occurred while getting the address";
    }

    private void showDetailedProducerActivity(String phoneNumber) {
        Intent intent = new Intent(ConsumerActivity.this, ProducerDetailsActivity.class);
        intent.putExtra("producer-phoneNumber", phoneNumber);
        startActivity(intent);
    }

    private void init() {
        location = (LatLng) Objects.requireNonNull(getIntent().getExtras()).get("location");
        username = getIntent().getExtras().getString("username");
        PojoUserCredentials userCredentials = new PojoUserCredentials(username, location);
        consumerInternal = SESSION.consumerLogin(userCredentials);
        if (getIntent().getBooleanExtra("sign-up", false)) {
            SESSION.writeData("consumers/" + consumerInternal.getUsername(), GSON.toJson(userCredentials));
        } else {
            // TODO : Implement login functionality
        }
    }
}
