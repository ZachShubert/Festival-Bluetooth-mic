package com.example.myapplication;

public class PairedDevices_Item {
    String name;
    String address;

    public PairedDevices_Item(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() { return address; }
}
