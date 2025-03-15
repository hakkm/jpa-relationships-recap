package com.khabir.model.inheritance.singletable;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.khabir.EnvLoader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SingleTableInheritance {
  /*
   * Tests Single Table Inheritance
   * Just one table for all classes
   * Person(id, person_type, name, grade, salary)
   */
  private EntityManagerFactory emf;
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPUSingleTableInheritance",
        EnvLoader.getPersistenceProperties());
    em = emf.createEntityManager();
  }

  @AfterEach
  public void tearDown() {
    if (em.isOpen()) {
      em.close();
    }
    if (emf.isOpen()) {
      emf.close();
    }
  }

  @Test
  public void testCreateStudentsAndTeachers() {
    Student student1 = new Student("omar", 10);
    Teacher teacher1 = new Teacher("ali", 1_000_000);

    em.getTransaction().begin();
    em.persist(student1);
    em.persist(teacher1);
    em.getTransaction().commit();

    Student foundStudent1 = em.find(Student.class, student1.getId());
    Teacher foundTeacher1 = em.find(Teacher.class, teacher1.getId());

    assertNotNull(foundStudent1);
    assertNotNull(foundTeacher1);
  }

  @Test
  public void testUpdateStudentAndTeacher() {
    Student student1 = new Student("omar", 10);
    Teacher teacher1 = new Teacher("ali", 1_000_000);

    em.getTransaction().begin();
    em.persist(student1);
    em.persist(teacher1);
    em.getTransaction().commit();

    em.getTransaction().begin();
    student1.setName("Updated Omar");
    student1.setGrade(12);
    teacher1.setName("Updated Ali");
    teacher1.setSalary(1_200_000);
    em.merge(student1);
    em.merge(teacher1);
    em.getTransaction().commit();

    Student updatedStudent1 = em.find(Student.class, student1.getId());
    Teacher updatedTeacher1 = em.find(Teacher.class, teacher1.getId());

    assertEquals("Updated Omar", updatedStudent1.getName());
    assertEquals(12, updatedStudent1.getGrade());
    assertEquals("Updated Ali", updatedTeacher1.getName());
    assertEquals(1_200_000, updatedTeacher1.getSalary());
  }

  @Test
  public void testDeleteStudentAndTeacher() {
    Student student1 = new Student("omar", 10);
    Teacher teacher1 = new Teacher("ali", 1_000_000);

    em.getTransaction().begin();
    em.persist(student1);
    em.persist(teacher1);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(student1);
    em.remove(teacher1);
    em.getTransaction().commit();

    Student deletedStudent1 = em.find(Student.class, student1.getId());
    Teacher deletedTeacher1 = em.find(Teacher.class, teacher1.getId());

    assertNull(deletedStudent1);
    assertNull(deletedTeacher1);
  }
}
