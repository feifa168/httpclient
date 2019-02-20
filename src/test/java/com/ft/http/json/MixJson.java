package com.ft.http.json;
//
//public class MixJson {
//}

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * jackson 复杂 对象集合 的几种简单转换
 * @author lenovo
 *
 * @param <T>
 */
public class MixJson<T>
{
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws JsonParseException,
            JsonMappingException, IOException
    {
        mapper.setVisibility(ALL, NONE)
                .setVisibility(FIELD, ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String josn = "{\"UserID\":1,\"LoginName\":\"唐工\",\"IsDelete\":0}";
        User u = mapper.readValue(josn, User.class);
        // User u=new MixJson<User>().jsonStreamConverObject(josn, User.class);
        System.out.println("转对象:" + u);

        // 转集合
        String josn2 = "[{\"UserID\":1,\"LoginName\":\"唐工\",\"IsDelete\":0}]";
        JavaType javaType = mapper.getTypeFactory().constructParametricType(
                List.class, User.class);
        List<User> me = mapper.readValue(josn2, javaType);
        System.out.println("转集合me:" + me);

        // 对象里有 集合 转换
        String josn3 = "{\"UserID\":1,\"LoginName\":\"唐工\",\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"super manager\"}]}";

        //User u3 = mapper.readValue(josn3, User.class); // 简单方式
        User u3=new MixJson<User>().jsonConverObject(josn3, User.class); //流方式
        System.out.println("转对象里有集合u3:" + u3);

        // 集合 对象 集合 转换
        String josn4 = "[{\"UserID\":1,\"LoginName\":\"唐工\",\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"super manager\"}]},{\"UserID\":2,\"LoginName\":\"唐工\",\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"super manager\"}]}]";
        JavaType javaType4 = mapper.getTypeFactory().constructParametricType(
                List.class, User.class);
        List<User> list = mapper.readValue(josn4, javaType4);
        System.out.println("集合里是对象 对象里有集合转换:" + list);
    }

    /***
     * 转对象
     * @param josn
     * @param clz
     * @return
     */
    public T jsonStreamConverObject(String josn, Class<T> clz)
    {

        T t = null;
        // ObjectMapper jacksonMapper = new ObjectMapper();
        InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(
                josn.getBytes()));
        BufferedReader streamReader = new BufferedReader(in);
        StringBuilder buff = new StringBuilder();
        String inputStr;
        try
        {
            while ((inputStr = streamReader.readLine()) != null)
                buff.append(inputStr);
            // ObjectMapper mapper = new ObjectMapper();
            t = mapper.readValue(buff.toString(), clz);

        } catch (IOException e)
        {

            e.printStackTrace();
        }

        return t;
    }

    /***
     * 转对象
     * @param josn
     * @param clz
     * @return
     */
    public T jsonConverObject(String josn, Class<T> clz)
    {

        T t = null;
        try
        {
            t = mapper.readValue(josn, clz);
        } catch (JsonParseException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 转集合
     * @param josn
     * @param clz
     * @return
     */
    public List<T> jsonConverList(String josn, Class<T> clz)
    {

        List<T> me = null;
        try
        {
            // jacksonMapper
            // .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            // jacksonMapper.enableDefaultTyping();
            // jacksonMapper.setVisibility(JsonMethod.FIELD,JsonAutoDetect.Visibility.ANY);
            // jacksonMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
            // false);//格式化
            // jacksonMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            // jacksonMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
            // false);

            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(List.class, clz);// clz.selGenType().getClass()

            me = mapper.readValue(josn, javaType);
        } catch (JsonParseException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return me;
    }
}

/**
 * output:
 * 转对象:User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, RoleList=null]
 * 转集合me:[User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, RoleList=null]]
 * 转对象里有集合u3:User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, RoleList=[Role [Roleid=0, Name=超级管理员, Show_Name=超级管理员, Remark=null, Type=1]]]
 * 集合里是对象 对象里有集合转换:[User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, RoleList=[Role [Roleid=0, Name=超级管理员, Show_Name=超级管理员, Remark=null, Type=1]]], User [UserID=2, LoginName=唐工, Truename=超级, Nickname=null, LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, RoleList=[Role [Roleid=0, Name=超级管理员, Show_Name=超级管理员, Remark=null, Type=1]]]]
 * */