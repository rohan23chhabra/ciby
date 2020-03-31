package com.waoss.ciby;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.waoss.ciby.utils.PojoItem;

import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.waoss.ciby.utils.CoronaUtils.getItems;

public class GenericListViewActivity extends AppCompatActivity {

    String itemType;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_list_view);
        init();
    }

    private void init() {
        itemType = Objects.requireNonNull(getIntent().getExtras()).getString("item-type");
        List<PojoItem> itemList = getItems(itemType);
        listView = findViewById(R.id.item_list);
        List<String> itemDescriptions = itemList
                .stream()
                .map(item -> item.getName() + "\n"  + item.getProducerUsername())
                .collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listview_item, R.id.item_description, itemDescriptions);
        listView.setAdapter(adapter);

    }
}
