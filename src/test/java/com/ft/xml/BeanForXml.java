package com.ft.xml;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeanForXml {
    private int age;
    private String naem;
    private String addr;

    @JsonCreator
    public BeanForXml(@JsonProperty("age") int age,
                      @JsonProperty("name") String naem,
                      @JsonProperty("addr") String addr) {
        this.age = age;
        this.naem = naem;
        this.addr = addr;
    }

}
