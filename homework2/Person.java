package com.company;

import java.util.Objects;

public abstract class Person extends Men{
    public String name;
    Person(String name, String gender){
        super(gender);
        this.name = name;
    }
    public void getInfo(String name){
        System.out.println(name);
        }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
