package com.waoss.ciby;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.waoss.ciby.utils.CoronaUtils;
import com.waoss.ciby.utils.PojoItem;

import static com.waoss.ciby.utils.CoronaUtils.*;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameField;
    private EditText itemTypeField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        itemNameField = findViewById(R.id.item_name);
        itemTypeField = findViewById(R.id.item_type);
    }

    public void addItemOnClick(View view) {
        final String itemName = itemNameField.getText().toString();
        final String itemType = itemTypeField.getText().toString();
        SESSION.addItem(new PojoItem(itemName, itemType, 0, System.getProperty("user.phonenumber")));
    }
}
