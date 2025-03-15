package com.khabir.model.inheritance.tableperclass;

import jakarta.persistence.Entity;

@Entity
public class Teacher extends Person {
  private int salary;

  public Teacher() {
  }

  public Teacher(String name, int salary) {
    super(name);
    this.salary = salary;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "Teacher [salary=" + salary + "]";
  }
}
