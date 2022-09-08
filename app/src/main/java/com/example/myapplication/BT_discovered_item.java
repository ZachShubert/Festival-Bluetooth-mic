package com.example.myapplication;

public class BT_discovered_item {
    String name;
    String address;

    public BT_discovered_item(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() { return address; }
}
