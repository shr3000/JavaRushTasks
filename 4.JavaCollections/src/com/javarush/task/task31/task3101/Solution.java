package com.javarush.task.task31.task3101;

/* 
Проход по дереву файлов
*/
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution {
    public static void main(String[] args) throws IOException{
        Path filePath = Paths.get(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File file1 = new File(resultFileAbsolutePath.getParent()+ "/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath, file1);
        List<File> files = new ArrayList<>();

        Files.walkFileTree(filePath, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                if (!filePath.equals(resultFileAbsolutePath)) {
                    File file = filePath.toFile();
                    if (file.length() > 50) {
                        FileUtils.deleteFile(file);
                    } else {
                        files.add(file);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.toFile().list().length == 0) {
                    dir.toFile().delete();
                }
                return FileVisitResult.CONTINUE;
            }
        });
        Collections.sort(files, Comparator.comparing(File::getName));

        try (FileOutputStream outputStream = new FileOutputStream(file1)){
            for (File f : files) {
                try(FileInputStream inputStream = new FileInputStream(f)) {
                    while (inputStream.available() > 0) {
                        outputStream.write(inputStream.read());
                        outputStream.flush();
                    }
                    outputStream.write('\n');
                }
            }
        } catch (IOException e) {}
    }

    public static void deleteFile(File file) {
        if (!file.delete()) System.out.println("Can not delete file with name " + file.getName());
    }
}
