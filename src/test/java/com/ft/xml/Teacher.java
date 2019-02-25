package com.ft.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class Teacher {
    @JacksonXmlProperty(localName = "TypeCode1")
    private TeacherType teacherTypeCode;
    private String name;
    private String gender;
    private String age;

    public TeacherType getTeacherTypeCode() {
        return teacherTypeCode;
    }

    public void setTeacherTypeCode(TeacherType teacherTypeCode) {
        this.teacherTypeCode = teacherTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
