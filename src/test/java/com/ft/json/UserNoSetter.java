package com.ft.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserNoSetter {
    public static class Address {
        private String city;
        private List<String> roads;

        public Address() {
            this.city = "beging";
            this.roads = null;
        }
        public Address(String city, List<String> roads) {
            this.city = city;
            this.roads = roads;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<String> getRoads() {
            return roads;
        }

        public void setRoads(List<String> roads) {
            this.roads = roads;
        }

//        @JsonCreator
//        public Address(@JsonProperty("city") String city,
//                       @JsonProperty("roads") List<String> roads) {
//            this.city = city;
//            this.roads = roads;
//        }
    }


    @JsonCreator
    public UserNoSetter(@JsonProperty("_name123") String name,
                        @JsonProperty("score1") HashMap<String, Integer> score,
                        @JsonProperty("addr1") Address addr) {
        this.name = name;
        this._score = score;
        this._addr = addr;
    }

    @JsonProperty("_name")
    private String name;
    private HashMap<String, Integer> _score;
    private Address _addr;
}
