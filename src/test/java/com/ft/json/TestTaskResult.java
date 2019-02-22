package com.ft.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
