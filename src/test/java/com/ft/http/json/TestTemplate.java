package com.ft.http.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ft.http.v3.config.TaskScanConfig;
import com.ft.http.v3.scantemplate.ScanTemplate;
import io.netty.buffer.ByteBuf;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTemplate {

    private ObjectMapper mapper = new ObjectMapper();
    private XmlMapper xmlMapper = new XmlMapper();

    @Before
    public void before() {
        //字段为null，自动忽略，不再序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;

        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
    }

    public <T> T buildResultObject(byte[] result, Class<?> className) throws IOException {
        if (null == result) {
            return null;
        }

        T t = (T)mapper.readValue(result, className);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    public <T> T buildTemplate(String configFile, Class<?> className) throws IOException {
        T t = (T)xmlMapper.readValue(new File(configFile), className);
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    public <T> T buildTemplate(byte[] result, Class<?> className) throws IOException {
        T t = (T)xmlMapper.readValue(result, className);
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    @Test
    public void testTemplateDefault() {
        try {
            FileInputStream fis = new FileInputStream("template\\default.json");
            FileChannel fc = fis.getChannel();
            ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
            int readLen = fc.read(buf);

            ScanTemplate st = buildResultObject(buf.array(), ScanTemplate.class);

            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(st);

            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(st);
            System.out.println(xmlString);
            st = xmlMapper.readValue(xmlString.getBytes(), ScanTemplate.class);

            System.out.println("=====================end");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTemplateDiscover() {
    }

    @Test
    public void testTemplateCustom() {
    }
}
