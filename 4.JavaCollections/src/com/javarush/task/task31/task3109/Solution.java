package com.javarush.task.task31.task3109;

import java.io.File;
import java.io.*;
import java.util.Properties;

/* 
Читаем конфиги
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Properties properties = solution.getProperties("/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("D:/SecretFolder/a.properties");
        properties.list(System.out);
//        System.out.println(properties.getProperty("name"));
    }

    public Properties getProperties(String fileName) {

        Properties properties = new Properties();

//        String workDir = System.getProperty("user.dir");
//        String fullFilename = "4.JavaCollections" + File.separator + fileName;

        File file = new File(fileName);
        try{
        if (fileName.endsWith(".xml")) properties.loadFromXML(new FileInputStream(file));
        else properties.load(new FileReader(file));

        }catch (Exception e){
        }
        return properties;
    }
}
