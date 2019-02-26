package com.ft.xml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "baseforxml")
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class BeanForXml {
    private int age;
    private String name;
    @JacksonXmlElementWrapper(localName = "addrs")     // 不能与JsonProperty名称一样
    @JacksonXmlProperty(localName = "addr")
    //@JsonProperty("addrs")
    private List<String> addrs;

    @JsonCreator
    public BeanForXml(@JsonProperty("age")   int age,
                      @JsonProperty("name")  String name,
                      @JsonProperty("addrsJson") List<String> addrs) {
        this.age = age;
        this.name = name;
        this.addrs = addrs;
    }

//    public BeanForXml(int age,
//                      String name,
//                      List<String> addrs) {
//        this.age = age;
//        this.name = name;
//        this.addrs = addrs;
//    }

//    public BeanForXml() {
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAddrs(List<String> addrs) {
//        this.addrs = addrs;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public List<String> getAddrs() {
//        return addrs;
//    }
}
