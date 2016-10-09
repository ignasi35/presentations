package com.marimon.railways.model;

public class Customer {
    private final Address address;

    public Customer(String name, Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }
}
