package com.waoss.ciby;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.waoss.ciby.apis.UserType;
import com.waoss.ciby.firebase.FirebaseSession;
import com.waoss.ciby.utils.PojoItem;

import java.util.Objects;

public class ProducerActivity extends AppCompatActivity {

    private FirebaseSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);
        session = new FirebaseSession(UserType.PRODUCER);
    }

    public void addItemOnClick(View view) {
        session.addItem(new PojoItem("rabdi", "food", 1,
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()));
    }
}
