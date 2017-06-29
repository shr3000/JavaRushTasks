package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Set<Character> set = new TreeSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String s;
        while ((s = reader.readLine()) != null){
            String s1 = s.replaceAll("[^A-Za-z]", "").toLowerCase();
            for (Character c: s1.toCharArray()) {
                set.add(c);
            }
        }
        int count = 0;
        for (Character c: set) {
            System.out.print(c);
            count++;
            if (count == 5) break;
        }
    }
}
