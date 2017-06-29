package com.javarush.task.task35.task3507;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(
                Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                        + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<? extends Animal> set = new HashSet<>();
        String d = "C:/JavaRushTasks/out/production/4.JavaCollections/com/javarush/task/task35/task3507/data/Cat";
        System.out.println(d);
        try{
            Class cl = Class.forName("com.javarush.task.task35.task3507.data.Cat");
            System.out.println(cl.newInstance().getClass());

            Class clc = Class.forName("com.javarush.task.task35.task3507.data.Cat");
            System.out.println(clc.newInstance().getClass());

            Class clc1 = Class.forName(d);
            System.out.println(clc1.newInstance().getClass());
        }catch (Exception e){
            System.out.println("да");
        }
        File file = new File(pathToAnimals);
        System.out.println(pathToAnimals);
        if (file.isDirectory()){
            String[] strings = file.list();
            for (String s: strings){
                System.out.println(s);
                try {
//                    Class clazz = Class.forName(pathToAnimals + "/" + s);
//                    System.out.println(clazz.newInstance().getClass());
//                    set.add(clazz.newInstance());

                } catch (Exception e) {
                    System.out.println("ytn");
                }
            }
        }

        return set;
    }
}
