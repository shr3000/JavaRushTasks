package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://www.amigo.com/ship/secretPassword.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
//        URL url=new URL(urlString);
//        InputStream inputStream=url.openStream();
//        Path tmp=Files.createTempFile("temp-",".tmp");
//        Files.copy(inputStream,tmp);
//        String fieName=urlString.substring(urlString.lastIndexOf("/"));
//        String dir=downloadDirectory.toString();
//        Path moveTo=Paths.get(dir+fieName);
//        Files.move(tmp,moveTo);
//        return Paths.get(downloadDirectory.toString()+urlString.substring(urlString.lastIndexOf("/")));
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Path tepmFile = Files.createTempFile("tmp-", ".tmp");
        Files.copy(inputStream, tepmFile, StandardCopyOption.REPLACE_EXISTING);
        Path newPath = Paths.get(downloadDirectory.toString(), Paths.get(url.getFile()).getFileName().toString());
        Files.move(tepmFile, newPath);
        // implement this method
        return newPath;
    }
}
