package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        int result = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (Character c:  s.toCharArray()){
            String s1 = String.valueOf(c);
            switch (s1) {
                case "I": list.add(1); break;
                case "V": list.add(5); break;
                case "X": list.add(10); break;
                case "L": list.add(50); break;
                case "C": list.add(100); break;
                case "D": list.add(500); break;
                case "M": list.add(1000); break;
            }
        }
        for (int i = 0; i < list.size(); i++){
            if (i <= list.size() - 2){
                if (list.get(i) >= list.get(i+1)) result += list.get(i);
                else result -= list.get(i);
            }else result += list.get(i);
        }
        return result;
    }
}
