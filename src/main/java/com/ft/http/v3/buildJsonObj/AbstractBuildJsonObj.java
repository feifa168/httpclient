package com.ft.http.v3.buildJsonObj;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AbstractBuildJsonObj<T> implements BuildJsonObj<T> {
    @Override
    public T buildObj(byte[] jsonByte, String className) throws ClassNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return (T)mapper.readValue(jsonByte, Class.forName(className));
    }
}
