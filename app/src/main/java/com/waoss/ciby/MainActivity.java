package com.waoss.ciby;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.waoss.ciby.apis.UserType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void emergencyOnClick(View view) {
        Log.d("main-activity", "emergencyyyy");
        Intent intent = new Intent(MainActivity.this, EmergencyActivity.class);
        startActivity(intent);
    }

    public void regularOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
        startActivity(intent);
    }
}
