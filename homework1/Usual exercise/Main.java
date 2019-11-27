package com.company;

public class Main {

    public static void main(String[] args) {
        int a = 5;
        int b = 5;
        int postIncrement = a++ + a++; // = 5 + 6; last 'a' value changed at 'a++' is never used;
        int preIncrement = ++b + ++b; // = 6 + 7
        System.out.println("sum 5+5 using post increments = " + postIncrement + " /only the first number is incremented/");
        System.out.println("sum 5+5 using pre increments = " + preIncrement + " /both numbers are incremented/");
        int c = -1;
        while (c < 25) {
            c++;
            System.out.print(c + " ");
        }
        System.out.println();

        int step = 2;
        for (int i = 2; i < 21; i = i + step) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 12; i < 21; i = i + step) {
            System.out.print(i + " ");
        }
    }
}

