package com.khabir.model.inheritance.joinedtable;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
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
