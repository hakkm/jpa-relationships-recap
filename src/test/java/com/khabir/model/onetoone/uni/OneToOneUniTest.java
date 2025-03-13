package com.khabir.model.onetoone.uni;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.khabir.EnvLoader;
import com.khabir.model.onetoone.uni.Course;
import com.khabir.model.onetoone.uni.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class OneToOneUniTest {
  /*
   * Tests OneToOne uni-directional
   * Just Two tables was created in the database: student and course
   * Student(id, name, course_id), course(id, name)
   */
  private EntityManagerFactory emf;
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPUOneToOneUni");
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
  public void testCreateStudentAndCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    Student foundStudent = em.find(Student.class, student.getId());
    assertNotNull(foundStudent);
    assertNotNull(foundStudent.getCourse());
    assertEquals("Math", foundStudent.getCourse().getName());
  }

  @Test
  public void testUpdateStudentAndCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);

    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    course.setName("Advanced Math");
    student.setName("Updated Omar");
    em.merge(student);
    em.getTransaction().commit();

    Student updatedStudent = em.find(Student.class, student.getId());
    assertEquals("Advanced Math", updatedStudent.getCourse().getName());
    assertEquals("Updated Omar", updatedStudent.getName());
  }

  @Test
  public void testDeleteStudentAndCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);

    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(student);
    em.getTransaction().commit();

    Student deletedStudent = em.find(Student.class, student.getId());
    Course deletedCourse = em.find(Course.class, course.getId());
    assertNull(deletedStudent);
    assertNull(deletedCourse);
  }
}
