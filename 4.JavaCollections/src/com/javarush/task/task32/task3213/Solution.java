package com.javarush.task.task32.task3213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor Dpljr");
        System.out.println(decode(reader, -3));  //Hello Amigo


    }

    public static String decode(StringReader reader, int key) throws IOException {
        StringWriter stringWriter = new StringWriter();
        if (reader == null) return stringWriter.toString();
        BufferedReader reader1 = new BufferedReader(reader);
        String s;
        while ((s = reader1.readLine()) != null){
            char[] chars = s.toCharArray();

            for (char c: chars){
                c += key;
                stringWriter.write(String.valueOf(c));
            }
        }
        return stringWriter.toString();
    }
}
