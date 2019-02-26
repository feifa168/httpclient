package com.ft.xml;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ft.http.v3.config.DomainUserPwd;
import com.ft.http.v3.config.UserPwd;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.NewTask;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
class MyCre {

    @JsonCreator
    public MyCre(@JsonProperty("account") Object account,
                      @JsonProperty("description") String description) {
        this.account = account;
        this.description = description;
    }

    private Object account;
    private String description;
}

@JacksonXmlRootElement(localName = "myforaccount")
class MyForAccount {
    @JsonCreator
    public MyForAccount(@JsonProperty("accounts") List<MyCre> accounts) {
        this.accounts = accounts;
    }

//    @JacksonXmlElementWrapper(localName = "accounts")
//    @JacksonXmlProperty(localName = "account")
    private List<MyCre> accounts;
}
public class TestAccount {
    private XmlMapper xmlMapper = new XmlMapper();

    public TestAccount() {
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

    @Test
    public void testAs400() throws IOException {
        List<MyCre> objs = new ArrayList<>();
        objs.add(new MyCre(new DomainUserPwd("as400", "domain", "user", "pwd"), "describe"));
        objs.add(new MyCre(new UserPwd("telnet", "user", "pwd"), "describe2"));

        MyForAccount task = new MyForAccount(objs);
        String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
        System.out.println(xmlString);

        task = xmlMapper.readValue(xmlString, MyForAccount.class);
        xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
        System.out.println(xmlString);
    }

    @Test
    public void testJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //字段为null，自动忽略，不再序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;
        //XML标签名:使用骆驼命名的属性名，
        //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        mapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        List<MyCre> objs = new ArrayList<>();
        objs.add(new MyCre(new DomainUserPwd("as400", "domain", "user", "pwd"), "describe"));
        objs.add(new MyCre(new UserPwd("telnet", "user", "pwd"), "describe2"));
        MyForAccount task = new MyForAccount(objs);
        String xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
        System.out.println(xmlString);

        task = mapper.readValue(xmlString, MyForAccount.class);
        xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
        System.out.println(xmlString);
    }
}
