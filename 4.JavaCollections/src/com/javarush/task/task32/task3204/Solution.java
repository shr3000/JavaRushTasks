package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) throws Exception{
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String s = "qwertyuioplkjhgfdsazxcvbnm";
        char [] ss = s.toCharArray();
        String d = "1234567890";
        char [] dd = d.toCharArray();
        String S = "QWERTYUIOPLKJHGFDSAZXCVBNM";
        char [] SS = S.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        stringBuilder.append(ss[random.nextInt(ss.length)]);
        stringBuilder.append(dd[random.nextInt(dd.length)]);
        stringBuilder.append(SS[random.nextInt(SS.length)]);
        for (int i = 1; i <= 5; i++){
            int y = random.nextInt(3);
            switch (y) {
                case 0: {
                    stringBuilder.append(ss[random.nextInt(ss.length)]);
                    break;
                }
                case 1: {
                    stringBuilder.append(dd[random.nextInt(dd.length)]);
                    break;
                }
                case 2: {
                    stringBuilder.append(SS[random.nextInt(SS.length)]);
                    break;
                }
            }
        }

        outputStream.write(stringBuilder.toString().getBytes());
        return outputStream;
    }
}