package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.Map;
import java.util.HashMap;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
//        String newFile = "D:\\SecretFolder\\license.rtf";
//        String archive = "D:\\SecretFolder\\SecretFolder.zip";
        String newFile = args[0];
        String archive = args[1];
        Map<ZipEntry, byte[]> zipEntryMap = new HashMap<>();
        boolean isExist = false;
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(archive))){
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                String fileName = Paths.get(zipEntry.getName()).getFileName().toString();
                if (fileName.equals(newFile)) isExist = true;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[8 * 1024];
                int len;

                while ((len = zipInputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                zipEntryMap.put(zipEntry, bytes);
                zipEntry = zipInputStream.getNextEntry();
                byteArrayOutputStream.close();
            }
        } catch (Exception e) {}

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archive))) {
                if (!isExist) {
                    ZipEntry newFileZipEntry = new ZipEntry("new/" + Paths.get(newFile).getFileName().toString());
                    zipOutputStream.putNextEntry(newFileZipEntry);
                    Files.copy(Paths.get(newFile), zipOutputStream);
                    zipOutputStream.closeEntry();
                    for (Map.Entry<ZipEntry, byte[]> pair: zipEntryMap.entrySet()){
                        zipOutputStream.putNextEntry(pair.getKey());
                        zipOutputStream.write(pair.getValue());
                        zipOutputStream.closeEntry();
                    }
                } else {
                    for (Map.Entry<ZipEntry, byte[]> pair : zipEntryMap.entrySet()) {
                        String fileName = Paths.get(pair.getKey().getName()).getFileName().toString();
                        zipOutputStream.putNextEntry(pair.getKey());

                        if (!fileName.equals(newFile)) {
                            zipOutputStream.write(pair.getValue());
                        } else {
                           Files.copy(Paths.get(newFile), zipOutputStream);
                        }
                        zipOutputStream.closeEntry();
                    }
            }
        } catch (Exception e) {}
    }
}
