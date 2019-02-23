package com.ft.json;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.TaskResult;
import org.junit.Test;

import java.io.IOException;

public class TestTaskResult {
    @Test
    public void test1244() throws IOException {
        String jsonString = "{\"page\":{\"number\":0,\"size\":10,\"totalPages\":1,\"totalResources\":1},\"resources\":[{\"assets\":1,\"duration\":\"PT11M57.883S\",\"endTime\":\"2019-02-21T10:14:17.007Z\",\"engineId\":3,\"engineName\":\"Local scan engine\",\"id\":21,\"scanType\":\"Manual\",\"startTime\":\"2019-02-21T10:02:19.124Z\",\"startedBy\":null,\"status\":\"FINISHED\",\"vulnerabilities\":{\"critical\":9,\"moderate\":8,\"severe\":57,\"total\":74}}]}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        TaskResult result = mapper.readValue(jsonString, TaskResult.class);
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(jsonString);
    }

    @Test
    public void testCerdentialAccount() throws IOException {
        String jsonString = "{\n" +
                "\t\"account\": {\n" +
                "\t\t\"database\": \"string\",\n" +
                "\t\t\"domain\": \"string\",\n" +
                "\t\t\"ntlmHash\": \"string\",\n" +
                "\t\t\"password\": \"****\",\n" +
                "\t\t\"pemKey\": \"string\",\n" +
                "\t\t\"permissionElevation\": \"sudo\",\n" +
                "\t\t\"privateKeyPassword\": \"string\",\n" +
                "\t\t\"realm\": \"string\",\n" +
                "\t\t\"service\": \"ssh\",\n" +
                "\t\t\"sid\": \"string\",\n" +
                "\t\t\"username\": \"username\"\n" +
                "\t}\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        ;


        Credential.Account result = mapper.readValue(jsonString, Credential.Account.class);
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(jsonString);
    }

    @Test
    public void testCerdential() throws IOException {
        String jsonString = "{\n" +
                "\t\"account\": {\n" +
                "\t\t\"database\": \"string\",\n" +
                "\t\t\"domain\": \"string\",\n" +
                "\t\t\"ntlmHash\": \"string\",\n" +
                "\t\t\"password\": \"****\",\n" +
                "\t\t\"pemKey\": \"string\",\n" +
                "\t\t\"permissionElevation\": \"sudo\",\n" +
                "\t\t\"permissionElevationPassword\": \"pwd\",\n" +
                "\t\t\"permissionElevationUsername\": \"root\",\n" +
                "\t\t\"privateKeyPassword\": \"string\",\n" +
                "\t\t\"realm\": \"string\",\n" +
                "\t\t\"service\": \"ssh\",\n" +
                "\t\t\"sid\": \"string\",\n" +
                "\t\t\"username\": \"username\"\n" +
                "\t},\n" +
                "\t\"description\": \"描述\",\n" +
                "\t\"enabled\": false,\n" +
                "\t\"hostRestriction\": \"10.1.40.16\",\n" +
                "\t\"name\": \"名称\",\n" +
                "\t\"portRestriction\": 22\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        ;

        Credential result = mapper.readValue(jsonString, Credential.class);
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(jsonString);
    }
}
