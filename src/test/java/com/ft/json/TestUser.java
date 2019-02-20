package com.ft.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestUser {
    public static class TmpInnerClass {
        private String s1;
        private Integer i1;

        @JsonCreator
        public TmpInnerClass(@JsonProperty("s1") String s1,
                             @JsonProperty("i1") Integer i1) {
            this.s1 = s1;
            this.i1 = i1;
        }

        @Override
        public String toString() {
            return s1 + ", " + i1;
        }
    }

    public static class Class234 {
        private int age;
        private TmpInnerClass[] tc;

        @JsonCreator
        public Class234(@JsonProperty("age") int age,
                        @JsonProperty("tc") TmpInnerClass[] tc) {
            this.age = age;
            this.tc = tc;
        }

        @Override
        public String toString() {
            StringBuilder txt = new StringBuilder(128);
            txt.append("age=").append(age).append("[");
            for(TmpInnerClass tc1 : tc) {
                txt.append(", ").append(tc1);
            }
            txt.append("]");
            return txt.toString();
        }
    }

    @Test
    public void test1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User.Address addr = new User.Address("nanjing", "beijing roat");
        List<Integer> listI = new ArrayList<Integer>();
        listI.add(2);
        listI.add(5);
        addr.setListI(listI);

        User user = new User(addr, "tom");
        byte[] btuser = mapper.writeValueAsBytes(user);
        User user2 = mapper.readValue(btuser, User.class);
        System.out.println(mapper.writeValueAsString(user2));
    }

    @Test
    public void test2() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                ;

        TmpInnerClass[] tc = new TmpInnerClass[1];
        tc[0] = new TmpInnerClass("sss", 23);
        String jsonString = mapper.writeValueAsString(tc);
        System.out.println(jsonString);

        List<TmpInnerClass> ltc = new ArrayList<>();
        for (TmpInnerClass tc1 : tc) {
            ltc.add(tc1);
        }
        System.out.println(mapper.writeValueAsString(ltc));

        Class234 c234 = new Class234(22, tc);
        jsonString = mapper.writeValueAsString(c234);
        System.out.println(jsonString);

        c234 = mapper.readValue(jsonString, Class234.class);
        System.out.println(c234);
        System.out.println(mapper.writeValueAsString(c234));
    }
}
