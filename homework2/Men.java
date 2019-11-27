package com.company;

public abstract class Men {
    String gender;

    Men(String gender) {
        this.gender = gender;
    }

    abstract void genderIdentifier();
}
