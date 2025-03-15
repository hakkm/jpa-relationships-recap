package com.khabir.model.inheritance.tableperclass;

import jakarta.persistence.Entity;

@Entity
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
