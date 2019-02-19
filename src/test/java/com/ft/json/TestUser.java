package com.ft.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUser {
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
}
