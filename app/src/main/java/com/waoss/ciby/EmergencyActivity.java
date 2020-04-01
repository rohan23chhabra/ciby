package com.waoss.ciby;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class EmergencyActivity extends AppCompatActivity {

    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }

    public void hospitalOnClick(View view) {
        startZonalListViewActivity("hospital");
    }

    public void policeStationOnClick(View view) {
        startZonalListViewActivity("police");
    }

    private void startZonalListViewActivity(String type) {
        final Intent intent = new Intent(this, ZonalListViewActivity.class);
        intent.putExtra("emergency-type", type);
        startActivity(intent);
    }
}
