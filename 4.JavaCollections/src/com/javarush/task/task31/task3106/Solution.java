package com.javarush.task.task31.task3106;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) return;

        String resultFileName = args[0];
        int filePartCount = args.length - 1;
        String[] fileNamePart = new String[filePartCount];
        for (int i = 0; i < filePartCount; i++) {
            fileNamePart[i] = args[i + 1];
        }

        // sort by file name
        Arrays.sort(fileNamePart);

        List<FileInputStream> fisList = new ArrayList<>();
        for (int i = 0; i < filePartCount; i++) {
            fisList.add(new FileInputStream(fileNamePart[i]));
        }

        SequenceInputStream seqInStream = new SequenceInputStream(Collections.enumeration(fisList));

        ZipInputStream zipInStream = new ZipInputStream(seqInStream);
        FileOutputStream fileOutStream = new FileOutputStream(resultFileName);
        byte[] buf = new byte[1024 * 1024]; // 1MB buffer
        while (zipInStream.getNextEntry() != null) {
            int count;
            while ((count = zipInStream.read(buf)) != -1) {
                fileOutStream.write(buf, 0, count);
            }
        }
        seqInStream.close();
        zipInStream.close();
        fileOutStream.close();
//        File file = new File(args[0]);
//        Set<String> parts = new TreeSet<>();
//        for (int i = 1; i < args.length; i++) {
//            parts.add(args[i]);
//        }
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//
//            for (String s: parts){
//                ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(s)));
//                zipInputStream.getNextEntry();
//
//                byte[] buffer = new byte[zipInputStream.available()];
//
//                zipInputStream.read(buffer);
//                fileOutputStream.write(buffer);
//                zipInputStream.closeEntry();
////                zipInputStream.close();
//            }
//        fileOutputStream.close();
//        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
//
//            for (String s: parts){
//                try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(s)))){
//                    zipInputStream.getNextEntry();
//
//                    byte[] buffer = new byte[zipInputStream.available()];
//
//                    zipInputStream.read(buffer);
//                    fileOutputStream.write(buffer);
//                    zipInputStream.closeEntry();
//                }
//            }
//
//        }catch (Exception e){}
    }
}
