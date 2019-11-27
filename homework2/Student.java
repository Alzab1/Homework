package com.company;

import java.util.Objects;

public class Student extends Person implements Greetings {
    private int id;
    private String faculty;

    Student(String faculty, String name, int id, String gender) {
        super(name, gender);
        this.faculty = faculty;
        this.id = id;
    }

    protected void getInfo() {
        System.out.println("Faculty " + faculty + ", Student " + name + ", Gender " + gender + ", id " + id);
    }

    private String getGender() {
        return gender;
    }

    @Override
    void genderIdentifier() {
        if (gender.equals("man")) System.out.println("he is boy");
        if (gender.equals("women")) System.out.println("she is girl");
        if (gender.equals("")) System.out.println("third gender");
    }

    @Override
    public void greetings() {
        System.out.println("Hi, student! Hi, " + name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", faculty='" + faculty + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return id == student.id &&
                faculty.equals(student.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, faculty);
    }
}
