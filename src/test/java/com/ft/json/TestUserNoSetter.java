package com.ft.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class TestUserNoSetter {
    @Test
    public void test235() throws IOException {
        HashMap<String, Integer> score = new HashMap<>();
        score.put("yuwen", 99);
        score.put("english", 97);

        List<String> street = new ArrayList<>();
        street.add("jiangsu");
        street.add("nanjing");
        UserNoSetter.Address addr = new UserNoSetter.Address("beijing", street);
        UserNoSetter user = new UserNoSetter("tom", score, addr);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(ALL, NONE).setVisibility(FIELD, ANY);

        String jsonString = mapper.writeValueAsString(user);
        System.out.println(jsonString);

        UserNoSetter user2 = mapper.readValue(jsonString, UserNoSetter.class);
        System.out.println(mapper.writeValueAsString(user2));
    }
}
