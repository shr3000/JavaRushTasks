package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> list = new ArrayList<>();
        File path = new File(root);
        Queue<File> queue = new LinkedList<>();
        queue.add(path);
        while (!queue.isEmpty()){
            File file = queue.poll();
            if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    queue.add(f);
                }
            } else if (file.isFile()) list.add(file.getAbsolutePath());

        }
        return list;

    }

    public static void main(String[] args) throws IOException{
        for (String s: getFileTree("D:\\IntelEmbedded6.0"))
        {
            System.out.println(s);
        }
    }
}
