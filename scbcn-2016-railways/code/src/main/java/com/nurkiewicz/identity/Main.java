package com.nurkiewicz.identity;

import com.marimon.railways.model.Address;
import com.marimon.railways.model.Customer;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Address urgell = new Address("Urgell", Optional.of(127));
        Customer customer = new Customer("Mr Smith", urgell);




        byte[] bytes = customer
                .getAddress()
                .getStreet()
                .substring(0, 3)
                .toLowerCase()
                .getBytes();





        Identity<byte[]> idBytes = new Identity<>(customer)
                .map(Customer::getAddress)
                .map(Address::getStreet)
                .map((String s) -> s.substring(0, 3))
                .map(String::toLowerCase)
                .map(String::getBytes);





    }


}

