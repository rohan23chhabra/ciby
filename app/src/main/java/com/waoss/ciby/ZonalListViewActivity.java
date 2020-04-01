package com.waoss.ciby;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.waoss.ciby.utils.CoronaUtils;
import com.waoss.ciby.utils.PojoZone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.waoss.ciby.utils.CoronaUtils.*;

public class ZonalListViewActivity extends AppCompatActivity {

    private String emergencyType;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonal_list_view);
        init();
    }

    private void init() {
        emergencyType = getIntent().getExtras().getString("emergency-type");
        List<PojoZone> pojoZoneList = getZones();
        List<String> pojoZoneDescriptionList;
        switch (emergencyType) {
            case "hospital":
                pojoZoneDescriptionList = describeHospital(pojoZoneList);
                break;
            case "police":
                pojoZoneDescriptionList = describePolice(pojoZoneList);
                break;
            default:
                pojoZoneDescriptionList = Collections.emptyList();
        }
        listView = findViewById(R.id.zonal_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listview_zone, R.id.zone_element, pojoZoneDescriptionList);
        listView.setAdapter(adapter);
    }

    private List<String> describePolice(List<PojoZone> pojoZoneList) {
        final List<String> result = new ArrayList<>();
        pojoZoneList.forEach(pojoZone -> result.add(formatStrings(pojoZone.getCity(), pojoZone.getEmail(), pojoZone.getPoliceLandline())));
        return result;
    }

    private String formatStrings(String... args) {
        final StringBuilder buffer = new StringBuilder();
        for (String string : args) {
            buffer.append(string);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private List<String> describeHospital(List<PojoZone> pojoZoneList) {
        final List<String> result = new ArrayList<>();
        pojoZoneList.forEach(pojoZone -> result.add(formatStrings(pojoZone.getCity(), pojoZone.getHospitalName(), pojoZone.getHospitalLandline())));
        return result;
    }
}
