package com.waoss.ciby;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.waoss.ciby.apis.UserType;
import com.waoss.ciby.firebase.FirebaseSession;
import com.waoss.ciby.utils.PojoItem;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ProducerActivity extends AppCompatActivity {

    private FirebaseSession session;
    private String[] currentStrings = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);
        session = new FirebaseSession(UserType.PRODUCER);
    }

    public void addItemOnClick(View view) {
        final Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

}
