package com.company;

public class Example {
    static int a = 4;
    static int b = 17;
    static int c = 5;

    public static void main(String[] args) {
        Methods methods = new Methods();
        System.out.println(methods.minNumber(b, a));
        System.out.println(methods.isEven(a));
        System.out.println(methods.isEven(b));
        System.out.println(methods.squared(c));
        System.out.println(methods.cubed(c));
        Student student = new Student("Biochemistry", "Piter", 456854, "man");
        Student student1 = new Student("Economy", "Galina", 25452546, "women");
        student.genderIdentifier();
        student1.genderIdentifier(); //friendly method
        student.greetings(); //public method
        student1.getInfo(); //protected method

    }
}
