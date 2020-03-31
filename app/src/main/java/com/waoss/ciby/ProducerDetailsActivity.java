package com.waoss.ciby;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.reflect.TypeToken;
import com.waoss.ciby.utils.PojoItem;

import java.lang.reflect.Type;
import java.util.List;

import static com.waoss.ciby.utils.CoronaUtils.*;

public class ProducerDetailsActivity extends AppCompatActivity {

    private String producerPhoneNumber;

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
        startGeneralListViewActivity("groceries");
    }

    private void startGeneralListViewActivity(String itemType) {
        final Intent intent = new Intent(this, GenericListViewActivity.class);
        intent.putExtra("item-type", itemType);
        startActivity(intent);
    }

    public void milkButtonOnClick(View view) {
        startGeneralListViewActivity("milk-product");
    }

    public void medicineButtonOnClick(View view) {
        startGeneralListViewActivity("medicine");
    }

    public void fruitsButtonOnClick(View view) {
        startGeneralListViewActivity("fruits");
    }
}
