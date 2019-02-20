package com.ft.http.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyValue {
    public String name;
    public int age;
    // NOTE: if using getters/setters, can keep fields `protected` or `private`

    public String toString() {
        return "name "+name+", age "+age;
    }

    @Test
    public void testReadFromString2List() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonList = "[\"jack\",\"tom\"]";
        ArrayList<String> list = mapper.readValue(jsonList, ArrayList.class);
        System.out.println("from string " + jsonList + " to arraylist" + list);
    }
    @Test
    public void testReadFromString2List2() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonList = "[\"jack\",\"tom\"]";
        ArrayList<String> list = mapper.readValue(jsonList, new TypeReference<ArrayList<String> >(){});
        System.out.println("from string " + jsonList + " to arraylist" + list);
    }

    @Test
    public void testReadFromString2Map() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMap = "{\"id\":1, \"id2\":2}";
        HashMap<String, Integer> map = mapper.readValue(jsonMap, HashMap.class);
        System.out.println("from string " + jsonMap + " to map" + map);
    }
    @Test
    public void testReadFromString2Map2() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMap = "{\"id\":1, \"id2\":2}";
        HashMap<String, Integer> map = mapper.readValue(jsonMap, new TypeReference<Map<String, Integer> >(){});
        System.out.println("from string " + jsonMap + " to map" + map);
    }

    @Test
    public void testWriteAsString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Integer> mapSI = new HashMap<>();
        mapSI.put("age", 20);
        mapSI.put("id", 12);
        String string = mapper.writeValueAsString(mapSI);
        System.out.println("from map " + mapSI + " to string" + string);
    }
    @Test
    public void testWriteFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Integer> mapSI = new HashMap<>();
        mapSI.put("age", 20);
        mapSI.put("id", 12);
        String fileName = "jsonfile.json";
        File f = new File(fileName);
        mapper.writeValue(f, mapSI);
        Map<String, Integer> map2 = mapper.readValue(f, HashMap.class);
        System.out.println("from file " + fileName + " to string" + map2);
    }

    @Test
    public void testJsonString2List() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            //list<String>转json
            ArrayList<String> arrayString = new ArrayList<String>();
            arrayString.add("jack");
            arrayString.add("tom");
            String ljson = mapper.writeValueAsString(arrayString);
            System.out.println(ljson);
            //json转list<String>
            ArrayList<String> sList = mapper.readValue(ljson, ArrayList.class);
            System.out.println(sList);

            String jsonList = "[\"jack\",\"tom\"]";
            ArrayList<String> arr1234 = mapper.readValue(jsonList, ArrayList.class);
            ArrayList<String> arrwer = mapper.readValue(jsonList, new TypeReference<ArrayList<String> >(){});
            System.out.println("convert from string "+jsonList+" to arraylist "+ arr1234);
            jsonList = mapper.writeValueAsString(arr1234);
            System.out.println("convert from arraylist "+arr1234+" to string "+ jsonList);

            String jsonString = "[{\"id\":1},{\"id\":2}]";
            System.out.println("jsonString is "+jsonString);

            List<String> list = mapper.readValue(jsonString, ArrayList.class);
            System.out.println("from json string 2 list "+list);

            String jsonString2 = mapper.writeValueAsString(list);
            System.out.println("from list 2 json string is "+jsonString2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJsonFile2List() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = "[{\"id\":1},{\"id\":2}]";
            System.out.println("jsonString is "+jsonString);

            mapper.writeValue(new File("list.json"), jsonString);
            System.out.println("write "+jsonString+" to file");

            List<String> list = mapper.readValue("list.json", List.class);
            System.out.println("from json file 2 list is "+list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        MyValue value = null;
        try {
            value = mapper.readValue(new File("result.json"), MyValue.class);
            System.out.println("read from file "+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
// or:
        try {
            value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
            System.out.println("read from url "+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
// or:
        try {
            value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);
            System.out.println("read from string "+value);
            mapper.writeValue(new File("result.json"), value);
            byte[] jsonBytes = mapper.writeValueAsBytes(value);
            String jsonString = mapper.writeValueAsString(value);
            int a = 4;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String listJsonString = "[{\"id\":1},{\"id\":2}]";
            System.out.println("listJsonString is "+listJsonString);
            List<String> ids = mapper.readValue(listJsonString, List.class);
            System.out.println("from json string 2 list "+ids);

            mapper.writeValue(new File("list.json"), ids);

            List<String> json2List = mapper.readValue("list.json", List.class);
            System.out.println("json file 2 list is "+json2List);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("name", "tom");
        map.put("sex", "male");
        try {
            String mapJson = mapper.writeValueAsString(map);
            mapJson = "{\"sex\":\"male\",\"name\":\"tom\"}";
            System.out.println("map 2 json string is " + mapJson);
            HashMap<String, Integer> json2Map = mapper.readValue(mapJson, HashMap.class);
            System.out.println("json string 2 map "+json2Map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
