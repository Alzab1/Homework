package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int setSizeArray() {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            System.out.println("Please enter an array size a positive number!");
            if (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            number = scanner.nextInt();
        } while (number <= 0);
        return number;
    }

    public static void main(String[] args) {
        int[] myArr = new int[setSizeArray()];
        for (int i = 0; i < myArr.length; i++) {
            myArr[i] = (int) (Math.random() * 11);
        }
        int[] myArrayCopy = Arrays.copyOf(myArr, myArr.length);
        int[] myArrayCopy1 = Arrays.copyOf(myArr, myArr.length);
        int[] myArrayCopy2 = Arrays.copyOf(myArr, myArr.length);
        System.out.println("Our array: ");
        System.out.println(Arrays.toString(myArr));
        System.out.println("Our reverse array");
        int temp;
        for (int i = 0; i < myArr.length / 2; i++) {
            temp = myArr[myArr.length - 1 - i];
            myArr[myArr.length - 1 - i] = myArr[i];
            myArr[i] = temp;
        }
        System.out.println(Arrays.toString(myArr));
        System.out.println("The product of multiplication of all array elements");
        long prodOfMultiplication = 1;
        for (int value : myArr) {
            prodOfMultiplication = prodOfMultiplication * value;
        }
        System.out.println(prodOfMultiplication);
        System.out.println("Every third element of the array is doubles");
        int step = 3;
        int multiplier = 2;
        for (int i = step - 1; i < myArr.length; i = i + step) {
            myArr[i] = myArr[i] * multiplier;
        }
        System.out.println(Arrays.toString(myArr));
        System.out.println("Every third element of the array is doubles by other way");
        for (int i = 0; i < myArr.length; i++) {
            if ((i + 1) % step == 0) {
                myArr[i] = myArr[i] * multiplier;
            }
        }
        System.out.println(Arrays.toString(myArr));
        System.out.println("Zero element counter:");
        int counterZeros = 0;
        for (int value : myArr) {
            if (value == 0) {
                counterZeros++;
            }
        }
        if (counterZeros == 0) {
            System.out.println("This array have no zeros");
        } else {
            System.out.printf("This array have %d zero(s)", counterZeros);
            System.out.println();
        }
        System.out.println("Zero elements index(es):");
        if (counterZeros == 0) {
            System.out.println("This array have no zeros");
        }
        for (int i = 0; i < myArr.length; i++) {
            if (myArr[i] == 0) {
                System.out.print(i + " ");
                System.out.println();
            }
        }
        System.out.println("Neighbors swap places");
        int step1 = 2;
        for (int i = 0; i < myArr.length - 1; i = i + step1) {
            temp = myArr[i + 1];
            myArr[i + 1] = myArr[i];
            myArr[i] = temp;
        }
        System.out.println(Arrays.toString(myArr));
        System.out.println("Return our experimental array to its original state:");
        System.out.println(Arrays.toString(myArrayCopy));
        System.out.println("let's continue! Min array element:");
        Arrays.sort(myArrayCopy);
        int minElement = myArrayCopy[0];
        System.out.println(minElement);
        System.out.println("Max array element:");
        int maxElement = myArrayCopy[myArrayCopy.length - 1];
        System.out.println(maxElement);
        System.out.println("Index(s) of min element");
        for (int i = 0; i < myArrayCopy1.length; i++) {
            if (myArrayCopy1[i] == minElement) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("Index(s) of max element");
        for (int i = 0; i < myArrayCopy1.length; i++) {
            if (myArrayCopy1[i] == maxElement) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("Is this an increasing array?");
        System.out.println(isIncrease(myArrayCopy1));
        System.out.println("Half sum neighboring elements array:");
        System.out.println(Arrays.toString(myArrayCopy1) + " before");
        int buffer1;
        int buffer2 = 0;
        for (int i = 1; i < myArrayCopy1.length - 1; i++) {
            buffer1 = (myArrayCopy1[i - 1] + myArrayCopy1[i + 1]) / 2;
            if (i > 1) myArrayCopy1[i - 1] = buffer2;
            buffer2 = buffer1;
        }
        myArrayCopy1[myArrayCopy1.length - 2] = buffer2;
        System.out.println(Arrays.toString(myArrayCopy1) + " after");
        System.out.println("cyclic shift");
        System.out.println(Arrays.toString(myArrayCopy2) + "before");
        int shift = 2;
        int buffer3;
        int buffer4 = 0;
        for (int i = myArrayCopy2.length - 1; i > 1; i--) {
            buffer3 = (myArrayCopy2[i - shift]);
            if (i < myArrayCopy2.length - 1) myArrayCopy2[i + 1] = buffer4;
            buffer4 = buffer3;
        }
        myArrayCopy2[2] = buffer4;
        for (int i = 0; i < shift; i++) {
            myArrayCopy2[i] = 0;
        }
        System.out.println(Arrays.toString(myArrayCopy2) + "after");
    }

    public static boolean isIncrease(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i + 1]) return false;
        }
        return true;
    }
}
