package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/


public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object o = new Object();
        Number n = (Number)o;
        System.out.println(n);
    }

    public void methodThrowsNullPointerException() {
        String s = null;
        byte[] bytes = s.getBytes();
    }

    public static void main(String[] args) {
//        new VeryComplexClass().methodThrowsClassCastException();
        new VeryComplexClass().methodThrowsNullPointerException();
    }
}
