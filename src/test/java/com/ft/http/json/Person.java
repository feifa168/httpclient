package com.ft.http.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class Person {
    private String name;
    private String address;
    private String mobile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Test
    public void testPerson2Json() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person();
        person.setName("davenkin");
        person.setAddress("sdgf");
        String jsonString = objectMapper.writeValueAsString(person);
        System.out.println(jsonString);
    }

    @Test
    public void testJsong2Person() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue("{\"name\":\"davenkin\",\"address\":\"\"}", Person.class);

        System.out.println("name: " + person.getName());
        System.out.println("address: " + person.getAddress());
        System.out.println("mobile: " + person.getMobile());
    }
}

