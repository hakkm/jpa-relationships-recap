package com.khabir.model.onetomany.bi;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.khabir.EnvLoader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class OneToManyBiTest {
  /*
   * Tests OneToMany bi-directional
   * Just Two tables was created in the database: student and course
   * Student(id, name), Course(id, name, student_id)
   */
  private EntityManagerFactory emf;
  private EntityManager em;

  private Student student = new Student("omar");
  private Course course1 = new Course("Math");
  private Course course2 = new Course("Info");

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPUOneToManyBi", EnvLoader.getPersistenceProperties());
    em = emf.createEntityManager();

    student.setCourses(List.of(course1, course2));
    course1.setStudent(student);
    course2.setStudent(student);
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
  public void testCreateStudentAndCourses() {
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    Student foundStudent = em.find(Student.class, student.getId());
    assertNotNull(foundStudent);
    assertEquals(2, foundStudent.getCourses().size());
    assertEquals("Math", foundStudent.getCourses().get(0).getName());
  }

  @Test
  public void testUpdateStudentAndCourses() {
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    student.setName("Updated Omar");
    student.getCourses().get(0).setName("Advanced Math");
    em.merge(student);
    em.getTransaction().commit();

    Student updatedStudent = em.find(Student.class, student.getId());
    assertEquals("Updated Omar", updatedStudent.getName());
    assertEquals("Advanced Math", updatedStudent.getCourses().get(0).getName());
  }

  @Test
  public void testDeleteStudentFirst() {
    /*
     * It should fail because student is referenced in courses by foreign keys
     */
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(student);
    assertThrows(Exception.class, () -> em.getTransaction().commit());
  }

  @Test
  public void testDeleteCoursesThenStudent() {
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(course1);
    em.remove(course2);
    em.remove(student);
    em.getTransaction().commit();

    Student deletedStudent = em.find(Student.class, student.getId());
    Course foundCourse1 = em.find(Course.class, student.getCourses().get(0).getId());
    Course foundCourse2 = em.find(Course.class, student.getCourses().get(1).getId());
    assertNull(deletedStudent);
    assertNull(foundCourse1);
    assertNull(foundCourse2);
  }
}
