package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw");

        int cursor = Integer.parseInt(args[1]);
        String text = args[2];
        int textLength = text.getBytes().length;
        byte [] filePart = new byte[textLength];
        String t = "true";
        String f = "false";

        randomAccessFile.seek(cursor);
        randomAccessFile.read(filePart, 0, textLength);
//        System.out.println(convertByteToString(filePart));
        randomAccessFile.seek(randomAccessFile.length());
        if (text.equals(convertByteToString(filePart))) {
            randomAccessFile.write(t.getBytes());
        } else randomAccessFile.write(f.getBytes());
    }
    public static String convertByteToString(byte readBytes[]) throws UnsupportedEncodingException{
        return new String(readBytes, "UTF-8");
    }
}
