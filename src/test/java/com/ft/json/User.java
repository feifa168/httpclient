package com.ft.json;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static class Address {
        private String city;
        private String street;
        private List<Integer> listI;

        public Address() {
            this.city = "bejing";
            this.street = "chang an street";
            this.listI = new ArrayList<>();
        }
        public Address(String city, String street) {
            this.city = city;
            this.street = street;
            this.listI = new ArrayList<>();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public List<Integer> getListI() {
            return listI;
        }

        public void setListI(List<Integer> listI) {
            this.listI = listI;
        }
    }

    public User() {
        this.addr = new Address();
        this.name = "tom";
    }
    public User(Address addr, String name) {
        this.addr = addr;
        this.name = name;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddr() {
        return addr;
    }

    private Address addr;
    private String name;
}
