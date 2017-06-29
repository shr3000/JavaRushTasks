package com.javarush.task.task33.task3311;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/* 
Странная ошибка
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter stringWriter = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String sampleJsonString = "{\"id\":1,\"name\":\"first\",\"KEY#1\":\"VALUE#1\",\"KEY#3\":\"VALUE#3\",\"KEY#2\":\"VALUE#2\"}";
        RealBean realBean = objectMapper.readValue(sampleJsonString, RealBean.class);

//        System.out.println(realBean.getName());
//        for (Map.Entry<String, Object> pair: realBean.getAdditionalMap().entrySet()){
//            System.out.println(pair.getKey());
//        }

        objectMapper.writeValue(stringWriter, realBean);
        System.out.println(stringWriter.toString());
    }
}
