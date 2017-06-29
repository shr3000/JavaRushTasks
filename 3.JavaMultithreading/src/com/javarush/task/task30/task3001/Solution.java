package com.javarush.task.task30.task3001;


import java.math.BigInteger;

/*
Конвертер систем счислений
*/
public class Solution {
    public static void main(String[] args) {
//        System.out.print("Введите число для конвертирования: ");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String sNum = br.readLine();
//        int i = Integer.parseInt(sNum);
//        System.out.print("Выберите основание новой системы счисления: ");
//        String sNu = br.readLine();
//        int q = Integer.parseInt(sNu);
//        System.out.println("В выбранной системе счисления " + i + " будет равно " + Integer.toString(i, q));
        Number number = new Number(NumerationSystemType._10, "6");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._2);
        System.out.println(result);    //expected 110

        number = new Number(NumerationSystemType._16, "6df");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._8);
        System.out.println(result);    //expected 3337
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {

        BigInteger bigInteger = new BigInteger(number.getDigit(), number.getNumerationSystem().getNumerationSystemIntValue());
        String s = bigInteger.toString(expectedNumerationSystem.getNumerationSystemIntValue());
        return new Number(expectedNumerationSystem, s);
    }
}
