package com.waoss.ciby.utils;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.waoss.ciby.firebase.FirebaseSession;

import java.lang.reflect.Type;
import java.util.*;

public class CoronaUtils {
    public static final Gson GSON = new Gson();
    public static FirebaseSession SESSION = new FirebaseSession();

    public static List<PojoItem> getItems(String type) {
        String json = SESSION.readData("items", "?orderBy=\"type\"&equalTo=\"" + type + "\"");
        Log.d("jsoooooooooonnnnnnnn", json);
        Type listType = new TypeToken<Map<Integer, PojoItem>>() {
        }.getType();
        LinkedHashMap<Integer, PojoItem> linkedHashMap = GSON.fromJson(json, listType);
        //Log.d("lmao-0", linkedHashMap.get(0).toString());
        //Log.d("lmao-1", linkedHashMap.get(1).toString());
        //Log.d("lmao-2", linkedHashMap.get(2).toString());
        List<PojoItem> pojoItemList = new ArrayList<>();
        linkedHashMap.forEach((k, v) -> pojoItemList.add(v));
        printList(pojoItemList);
        //return GSON.fromJson(json, listType);
        return pojoItemList;
    }

    public static List<PojoZone> getZones() {
        String json = SESSION.readData("zones", "");
        Log.d("json-zone", json);
        Type listType = new TypeToken<List<PojoZone>>() {
        }.getType();
        List<PojoZone> result = GSON.fromJson(json, listType);
        return result;
    }


    private static void printList(List<PojoItem> pojoItems) {
        for (PojoItem item : pojoItems) {
            Log.d("pojolmao", item.toString());
        }
    }
}
