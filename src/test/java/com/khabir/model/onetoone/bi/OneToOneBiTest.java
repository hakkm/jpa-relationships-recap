package com.khabir.model.onetoone.bi;


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

public class OneToOneBiTest {
  /*
   * Tests OneToOne bi-directional
   * Just Two tables was created in the database: student and course
   * Student(id, name, course_id), Course(id, name, student_id)
   */
  private EntityManagerFactory emf;
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("defaultPUOneToOneBi", EnvLoader.getPersistenceProperties());
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
  public void testCreateCourseFromStudent() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    Student foundStudent = em.find(Student.class, student.getId());
    assertNotNull(foundStudent);
    assertNotNull(foundStudent.getCourse());
    assertEquals("Math", foundStudent.getCourse().getName());


    Course foundCourse = em.find(Course.class, course.getId());
    assertNotNull(foundCourse);
    assertNotNull(foundCourse.getStudent());
    assertEquals("omar", foundCourse.getStudent().getName());
  }


  @Test
  public void testCreateStudentFromCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);

    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    Student foundStudent = em.find(Student.class, student.getId());
    assertNotNull(foundStudent);
    assertNotNull(foundStudent.getCourse());
    assertEquals("Math", foundStudent.getCourse().getName());


    Course foundCourse = em.find(Course.class, course.getId());
    assertNotNull(foundCourse);
    assertNotNull(foundCourse.getStudent());
    assertEquals("omar", foundCourse.getStudent().getName());
  }

  @Test
  public void testUpdateStudent() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);

    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();

    em.getTransaction().begin();
    course.setName("Advanced Math");
    student.setName("Updated Omar");
    em.merge(student);
    em.getTransaction().commit();

    Student updatedStudent = em.find(Student.class, student.getId());
    assertEquals("Updated Omar", updatedStudent.getName());
    assertEquals("Advanced Math", updatedStudent.getCourse().getName());
  }


  @Test
  public void testUpdateCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);

    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    em.getTransaction().begin();
    course.setName("Advanced Math");
    student.setName("Updated Omar");
    em.merge(course);
    em.getTransaction().commit();

    Student updatedStudent = em.find(Student.class, student.getId());
    assertEquals("Updated Omar", updatedStudent.getName());
    assertEquals("Advanced Math", updatedStudent.getCourse().getName());
  }

  @Test
  public void testDeleteStudent() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);

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

  @Test
  public void testDeleteCourse() {
    Student student = new Student("omar");
    Course course = new Course("Math");
    student.setCourse(course);
    course.setStudent(student);

    em.getTransaction().begin();
    em.persist(course);
    em.getTransaction().commit();

    em.getTransaction().begin();
    em.remove(course);
    em.getTransaction().commit();

    Student deletedStudent = em.find(Student.class, student.getId());
    Course deletedCourse = em.find(Course.class, course.getId());
    assertNull(deletedStudent);
    assertNull(deletedCourse);
  }
}

