package com.marimon.railways.model;

import java.util.Optional;

public class Address {
    private final String street;
    private final Optional<Integer> number;

    public Address(String street, Optional<Integer> number) {
        this.street = street;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public Optional<Integer> getNumber() {
        return number;
    }
}
