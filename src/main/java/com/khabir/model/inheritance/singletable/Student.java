package com.khabir.model.inheritance.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("student")
public class Student extends Person {
    private int grade;

    public Student() {
    }

    public Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student [grade=" + grade + "]";
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

}
