package com.ft.http.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.task.NewTask;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestTask {
    @Test
    public void testNewTask() throws IOException {
        List<String> incAddrs = new ArrayList<>();
        incAddrs.add("172.16.39.22");
        List<String> excAddrs = new ArrayList<>();
        excAddrs.add("172.16.39.254");

        NewTask.Scan.Assets.Targets includedTargets = new NewTask.Scan.Assets.Targets(incAddrs);
        NewTask.Scan.Assets.Targets excludedTargets = new NewTask.Scan.Assets.Targets(excAddrs);
        NewTask.Scan.Assets assets = new NewTask.Scan.Assets(includedTargets, null);
        NewTask.Scan scan = new NewTask.Scan(assets);
        NewTask nt = new NewTask("description", 3, "normal", "SCAN_56_2019-02-20 11:14:21",
                scan, null, "full-audit-without-web-spider");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                //.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
        System.out.println(jsonString);

        nt = mapper.readValue(jsonString, NewTask.class);
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
        System.out.println(jsonString);
    }
}
