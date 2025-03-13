package com.khabir.model.manytomany.bi;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ManyToManyBiTest {
  /*
   * Tests ManyToMany bi-directional
   * Includes Join Table
   * Student(id, name), Course(id, name), StudentCourse(student_id, course_id)
   */
  private EntityManagerFactory emf;
  private EntityManager em;

  private Student student1 = new Student("omar");
  private Student student2 = new Student("ali");
  private Course course1 = new Course("Math");
  private Course course2 = new Course("Info");

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPUManyToManyBi");
    em = emf.createEntityManager();

    student1.setCourses(List.of(course1, course2));
    student2.setCourses(List.of(course1));
    course1.setStudents(List.of(student1, student2));
    course2.setStudents(List.of(student1));
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
  public void testCreateStudentsAndCourses() {
    em.getTransaction().begin();
    em.persist(student1);
    em.persist(student2);
    em.getTransaction().commit();

    Student foundStudent1 = em.find(Student.class, student1.getId());
    Student foundStudent2 = em.find(Student.class, student2.getId());
    assertNotNull(foundStudent1);
    assertNotNull(foundStudent2);
    assertEquals(2, foundStudent1.getCourses().size());
    assertEquals(1, foundStudent2.getCourses().size());
    assertEquals("Math", foundStudent1.getCourses().get(0).getName());
    assertEquals("Info", foundStudent1.getCourses().get(1).getName());
  }

  @Test
  public void testUpdateStudentsAndCourses() {
    em.getTransaction().begin();
    em.persist(student1);
    em.persist(student2);
    em.getTransaction().commit();

    em.getTransaction().begin();
    student1.setName("Updated Omar");
    student1.getCourses().get(0).setName("Advanced Math");
    em.merge(student1);
    em.getTransaction().commit();

    Student updatedStudent1 = em.find(Student.class, student1.getId());
    assertEquals("Updated Omar", updatedStudent1.getName());
    assertEquals("Advanced Math", updatedStudent1.getCourses().get(0).getName());
  }

  @Test
  public void testDeleteStudent() {
    em.getTransaction().begin();
    em.persist(student1);
    em.persist(student2);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(student1);
    em.getTransaction().commit();

    Student deletedStudent = em.find(Student.class, student1.getId());
    assertNull(deletedStudent);
  }

  @Test
  public void testDeleteCourse() {
    em.getTransaction().begin();
    em.persist(student1);
    em.persist(student2);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(course1);
    em.getTransaction().commit();

    Course deletedCourse = em.find(Course.class, course1.getId());
    assertNull(deletedCourse);
  }
}
