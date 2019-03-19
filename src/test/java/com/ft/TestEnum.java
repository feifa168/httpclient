package com.ft;

import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})// 注意！这里一定要用Field，这样注解才可以加载enum实例中!!!
@interface FieldConfig {
    // 表中字段约束信息（类型，长度，索引，是否为空，默认值）
    Class<?> value();                       // 类型
    int length() default 0;                 // 长度
    boolean index() default false;          // 索引
    boolean nullable() default false;       // 是否为空
    String defaults() default "";           // 默认值
}

enum myEnum1 {
    ONE("one"),
    TWO("two"),
    THREE("three");

    public String getName() {
        return name;
    }

    private String name;
    private myEnum1(String name) {
        this.name = name;
    }
}

enum myEnum2 {
    @FieldConfig(String.class)
    account,
    @FieldConfig(value = String.class, length = 12)
    name,
    @FieldConfig(value = String.class, index = true)
    profession,
    @FieldConfig(String.class)
    sex,
    @FieldConfig(int.class)
    level,
    @FieldConfig(value = long.class, index = true, nullable = true, defaults = "test")
    test,
    @FieldConfig(value = String.class, index = true, length = 6)
    testnew,
    ;
}

public class TestEnum {
    @Test
    public void test1() {
        String s = myEnum1.ONE.getName();
        myEnum1 me = myEnum1.valueOf("ONE");
        System.out.println(me);
        me = myEnum1.valueOf("four");
        System.out.println(me);
    }

    @Test
    public void test2() {
    }

    public Object getFieldInfo(Object enumm, String name) throws Exception{
        // 获取@FieldConfig注解
        String enumName = ((Enum<?>)enumm).name();  // 先获取枚举名
        Annotation annotation = enumm.getClass().getField(enumName).getAnnotation(FieldConfig.class); //在获取注解

        // 在获取想要的方法
        Method method = FieldConfig.class.getMethod(name);

        // 反射调用方法获取相关注解值
        Object result = method.invoke(annotation);

        return result;
    }
}
