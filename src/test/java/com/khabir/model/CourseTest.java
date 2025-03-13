package com.khabir.model;

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

public class CourseTest {
  private EntityManagerFactory emf;
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPU", EnvLoader.getPersistenceProperties());
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
  public void testCreateCourse() {
    Course course = new Course("Math");
    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    Course foundCourse = em.find(Course.class, course.getId());
    assertNotNull(foundCourse);
  }

  @Test
  public void testUpdateCourse() {
    Course course = new Course("Math");
    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    em.getTransaction().begin();
    course.setName("Advanced Math");
    em.merge(course);
    em.getTransaction().commit();

    Course updatedCourse = em.find(Course.class, course.getId());
    assertEquals("Advanced Math", updatedCourse.getName());
  }

  @Test
  public void testDeleteCourse() {
    Course course = new Course("Math");
    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(course);
    em.getTransaction().commit();

    Course deletedCourse = em.find(Course.class, course.getId());
    assertNull(deletedCourse);
  }

}
