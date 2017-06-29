package com.javarush.task.task39.task3902;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

/* 
Биты были биты
В процессе разработки сложного алгоритма кодирования возникла задача определить четное ли количество
единиц в двоичной записи числа.

Реализуй метод boolean isWeightEven(long number), который будет возвращать true или false в зависимости от того,
является ли количество единиц в двоичном представлении числа number четным или нечетным.

P.S. Постарайся использовать только побитовые операции, а также минимизировать время выполнения программы.
Требования:

1.	Метод isWeightEven должен возвращать true, если количество единиц в двоичном представлении анализируемого числа четное.
2.	Метод isWeightEven должен возвращать false, если количество единиц в двоичном представлении анализируемого числа нечетное.
3.	Время выполнения метода isWeightEven должно быть максимально близко к оптимальному.
4.	Метод isWeightEven должен быть публичным и статическим.
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please type in a number: ");

        long l = Long.parseLong(reader.readLine());
        String result = isWeightEven(l) ? "even" : "odd";
        System.out.println("Number of ones in a given number is " + result);

    }

    public static boolean isWeightEven(long number) {
        System.out.println(Long.toBinaryString(number));
        System.out.println(Long.bitCount(number));
        return Long.bitCount(number)%2 == 0;
//        BitSet bs = BitSet.valueOf(new long[] { number });
//        return bs.cardinality()%2 == 0;
//
//        int result = 0;
//        while (number != 0) {
//            System.out.println(number);
//                    System.out.println(Long.toBinaryString(number));
//                    System.out.println(Long.toBinaryString(number-1));
//
//            number &= number - 1;
//            System.out.println(number);
//                    System.out.println(Long.toBinaryString(number));
//
//            System.out.println("__________________");
//            result++;
//        }
//        return result%2 == 0;
//        return false;

    }
}
