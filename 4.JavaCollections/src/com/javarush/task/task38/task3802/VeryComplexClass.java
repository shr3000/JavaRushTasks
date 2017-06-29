package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class VeryComplexClass {
    public void veryComplexMethod()
            throws Exception
    {
        String s = "q";
        Date date = new SimpleDateFormat("").parse(s);
        System.out.println(Integer.parseInt(s));
        //напишите тут ваш код
    }

    public static void main(String[] args) {
        try {
            new VeryComplexClass().veryComplexMethod();
        }catch (Exception e){
            e.getCause();
            e.getMessage();
            e.printStackTrace();
            Arrays.deepToString(e.getSuppressed());
            System.out.print(e.getClass().getSimpleName());
        }
    }
}
