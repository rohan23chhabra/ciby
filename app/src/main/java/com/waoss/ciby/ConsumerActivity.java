package com.waoss.ciby;

import android.content.Intent;
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

import java.util.List;
import java.util.Objects;
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

        List<String> phoneNumbers = producers.stream().map(Producer.External::getPhoneNumber).collect(Collectors.toList());

        listView = findViewById(R.id.producer_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_producer, R.id.producer_phone_number, phoneNumbers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phoneNumber = (String) parent.getItemAtPosition(position);
                Log.d("click-click", phoneNumber);
                showDetailedProducerActivity(phoneNumber);
            }
        });
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
