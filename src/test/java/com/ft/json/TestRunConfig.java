package com.ft.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.config.RunConfig;
import org.junit.Test;

import java.io.IOException;

public class TestRunConfig {
    @Test
    public void test33kr4j() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        //字段为null，自动忽略，不再序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;

        RunConfig config = new RunConfig(true, true, "admin", "admin@123", "10.0.92.95", 443, 60, 5);
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(jsonString);

        config = mapper.readValue(jsonString, RunConfig.class);
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(jsonString);
    }
}
