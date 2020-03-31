package com.waoss.ciby;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ProducerDetailsActivity extends AppCompatActivity {

    String producerPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_details);

        init();
    }

    private void init() {
        producerPhoneNumber = getIntent().getExtras().getString("producer-phoneNumber");
        Log.d("producer-details", producerPhoneNumber);
    }

    public void groceryButtonOnClick(View view) {

    }

    public void milkButtonOnClick(View view) {

    }

    public void medicineButtonOnClick(View view) {

    }

    public void fruitsButtonOnClick(View view) {

    }
}
