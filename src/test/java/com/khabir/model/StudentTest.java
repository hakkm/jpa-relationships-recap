package com.khabir.model;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class StudentTest {
  private EntityManagerFactory emf;
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPU");
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
  public void testCreateStudent() {
    Student student = new Student("omar");
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    Student foundStudent = em.find(Student.class, student.getId());
    assertNotNull(foundStudent);
  }

  @Test
  public void testUpdateStudent() {
    Student student = new Student("omar");
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    student.setName("Omar Updated");
    em.merge(student);
    em.getTransaction().commit();

    Student updatedStudent = em.find(Student.class, student.getId());
    assertEquals("Omar Updated", updatedStudent.getName());
  }

  @Test
  public void testDeleteStudent() {
    Student student = new Student("omar");
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(student);
    em.getTransaction().commit();

    Student deletedStudent = em.find(Student.class, student.getId());
    assertNull(deletedStudent);
  }
}
