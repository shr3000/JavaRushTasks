package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please input your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;
        int max = 0;
        Set<Character> set = new HashSet<>();
        int count = 0;
        char[] characters = s.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            set.add(characters[i]);

            if (set.size() - 1 == count) count ++;
            else {
                if (set.size() > max){
                    max = set.size();
                    set.clear();
                    set.add(characters[i]);
                    count = 0;
                }
            }
        }
        if(count > max) max = count;
        String e = s.substring(1);
        int maxR = lengthOfLongestUniqueSubstring(e);
        return max > maxR ? max: maxR;
    }
}
