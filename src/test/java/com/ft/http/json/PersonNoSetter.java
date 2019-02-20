package com.ft.http.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIgnoreProperties(value = {"mobile","name"},ignoreUnknown = true)
public class PersonNoSetter {
    private String name;
    private String address;
    private String mobile;

    @JsonCreator
    public PersonNoSetter(@JsonProperty(value="name", required=true) String name,
                  @JsonProperty(value="address", required=true) String address,
                  @JsonProperty(value="mobile") String mobile) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
    }

    public String toString() {
        return "{\"name\":\""+name +"\", \"address\":\""+address +"\", \"mobile\":\""+mobile+"\"}";
    }

    public static void obj2Json() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(ALL, NONE)
                .setVisibility(FIELD, ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        ;
        PersonNoSetter person = new PersonNoSetter("name", "", null);
        System.out.println(objectMapper.writeValueAsString(person));
    }

    public static void json2Obj() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(ALL, NONE)
                .setVisibility(FIELD, ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        ;
        PersonNoSetter person = objectMapper.readValue("{\"name\":\"davenkin\",\"address\":\"\"}", PersonNoSetter.class);

        System.out.println(person.toString());
    }

    public static void main(String[] args) throws IOException {
        PersonNoSetter.obj2Json();
        PersonNoSetter.json2Obj();
    }
}
