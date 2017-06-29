package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {

    public void recursion(int n) {
        String s = "";
        for (int i = 2; i <= n; i++){
            if (n%i == 0) {
                s = s + i+ " ";
                if (i == n) System.out.println(s.trim());
                else System.out.print(s);
                recursion(n/i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Solution().recursion(16);
    }
}
