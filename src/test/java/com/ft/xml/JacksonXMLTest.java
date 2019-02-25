package com.ft.xml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class JacksonXMLTest {
    private XmlMapper init() {
        XmlMapper xmlMapper = new XmlMapper();
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

        return xmlMapper;
    }

    @Test
    public void testBeanForXml() throws IOException {
        XmlMapper xmlMapper = init();

        String encoding = "utf-8";
        String xmlHead = "<?xml version=\"1.0\" encoding=\""+encoding+"\" ?>\n";
        BeanForXml bfx = new BeanForXml(20, "李大壮", null);
        String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bfx);
        xmlString = new String(xmlString.getBytes(encoding), encoding);
        System.out.println(xmlString);

        String xmlString2 = xmlHead+"<BeanForXml>\n" +
                "  <age>20</age>\n" +
                "  <age2>30</age2>\n" +
                "  <Naem>李大壮</Naem>\n" +
                "</BeanForXml>";
        bfx = xmlMapper.readValue(xmlString2, BeanForXml.class);
        xmlString2 = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bfx);
        System.out.println(xmlString2);
    }
    @Test
    public void testStudent() throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        Student stu = new Student();
        stu.setId("3");
        stu.setName("tom");
        String xmlString = xmlMapper.writeValueAsString(stu);
        System.out.println(xmlString);
    }

    @Test
    public void testGroup() throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        //序列化 bean--->xml
        Group group = new Group();  //忍者班级

        Teacher teacher = new Teacher();
        teacher.setTeacherTypeCode(new TeacherType("0","严师"));
        teacher.setName("卡卡西");
        teacher.setAge("25");
        teacher.setGender("1");

        Student student1 = new Student();
        student1.setId("001");  //学号
        student1.setName("鸣人");
        student1.setAge("18");
        student1.setGender("1");

        Student student2 = new Student();
        student2.setId("002");  //学号
        student2.setName("佐助");
        student2.setAge("18");
        student2.setGender("1");

        Student student3 = new Student();
        student3.setId("003");  //学号
        student3.setName("小樱");
        student3.setAge("18");
        student3.setGender("0");

        group.setTeacher(teacher);
        group.setStudent(Arrays.asList(student1,student2,student3));

        String result = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(group);
        System.out.println("序列化结果：\n" + result);


        Group group2 = null;
        try {
            group2 = xmlMapper.readValue(result, Group.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(group2);


    }
}
