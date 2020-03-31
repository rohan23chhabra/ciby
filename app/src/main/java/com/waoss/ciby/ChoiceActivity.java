package com.waoss.ciby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.waoss.ciby.apis.UserType;

import static com.waoss.ciby.apis.UserType.*;

public class ChoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void producerOnClick(View view) {
        startLocationActivity(PRODUCER);
    }

    private void startLocationActivity(final UserType userType) {
        final Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("user-type", userType.toString());
        startActivity(intent);
    }

    public void consumerOnClick(View view) {
        startLocationActivity(CONSUMER);
    }
}
