package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Path path = Paths.get(reader.readLine());
        SearchFileVisitor searchFileVisitor = new Solution().new SearchFileVisitor();
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, searchFileVisitor);
            System.out.println("Всего папок - " + (searchFileVisitor.getCountFolder()-1));
            System.out.println("Всего файлов - " + searchFileVisitor.getCountFile());
            System.out.println("Общий размер - " + searchFileVisitor.getAllSiie());
        }
        else {
            System.out.println(String.format("%s - не папка", path.toString()));
        }
    }

    public class SearchFileVisitor extends SimpleFileVisitor<Path> {

        private int countFolder = 0;
        private int countFile = 0;
        private long allSiie = 0;

        public int getCountFolder() {
            return countFolder;
        }

        public int getCountFile() {
            return countFile;
        }

        public long getAllSiie() {
            return allSiie;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (Files.isDirectory(dir)) countFolder++;
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (Files.isRegularFile(file)) {
                countFile++;
                allSiie += Files.size(file);
            }
            return super.visitFile(file, attrs);

        }
    }
}
