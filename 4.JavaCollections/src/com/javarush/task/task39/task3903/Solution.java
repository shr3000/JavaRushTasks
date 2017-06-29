package com.javarush.task.task39.task3903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

/* 
Неравноценный обмен
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Please type in a number: ");

        long number = Long.parseLong(reader.readLine());
        System.out.println("Please type in first index: ");
        int i = Integer.parseInt(reader.readLine());
        System.out.println("Please type in second index: ");
        int j = Integer.parseInt(reader.readLine());

        System.out.println("The result of swapping bits is " + swapBits(number, i, j));
    }

    public static long swapBits(long number, int i, int j) {
//        System.out.println(Long.toBinaryString(number));
        BitSet bs = BitSet.valueOf(new long[] { number });

        boolean one = bs.get(i);
        boolean two = bs.get(j);

        bs.set(i, two);
        bs.set(j, one);
        return bs.toLongArray()[0];

//        System.out.println(Long.toBinaryString(number ^ 1 << i));
//        System.out.println(Long.toBinaryString(number ^ 2 | number));
//        System.out.println(Long.toBinaryString(number ^ 3));
//        System.out.println(Long.toBinaryString(number ^ 4));
//        System.out.println(Long.toBinaryString(number ^ i << j));
//        System.out.println(Long.toBinaryString(number ^ j << i));
//        System.out.println(Long.toBinaryString(bs.toLongArray()[0]));

//        return 0;
    }
}
