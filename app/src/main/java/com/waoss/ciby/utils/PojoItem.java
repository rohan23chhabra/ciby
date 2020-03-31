package com.waoss.ciby.utils;

import com.waoss.ciby.apis.Item;

public class PojoItem implements Item {

    private String name;
    private String type;
    private String producerUsername;

    public PojoItem(String name, String type, int quantity, String producerUsername) {
        this.name = name;
        this.type = type;
        this.producerUsername = producerUsername;
    }

    public PojoItem() {
        this.name = "";
        this.type = "";
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getProducerUsername() {
        return producerUsername;
    }
}
