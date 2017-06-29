package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        String [] strings = {"1", "3", "9", "27", "81", "243", "729", "2187"};
        BigInteger bigInteger = new BigInteger(String.valueOf(number));
        StringBuilder builder = new StringBuilder(bigInteger.toString(3)).reverse();
        builder.append("0");
        char [] chars = builder.toString().toCharArray();
        builder.delete(0, builder.length());
        builder.append(number);
        builder.append(" =");
        int ostatok = 0;
        for (int i = 0; i < chars.length; i++) {
            int x = Character.getNumericValue(chars[i]) + ostatok;
            if (x == 1) {
                builder.append(" + ");
                builder.append(strings[i]);
                ostatok = 0;
            } else if (x == 2) {
                builder.append(" - ");
                builder.append(strings[i]);
                ostatok = 1;
            } else if (x == 3) {
                ostatok = 1;
            }
        }
        System.out.println(builder);
    }
}