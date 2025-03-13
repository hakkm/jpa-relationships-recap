# JPA Relationships Example

This project demonstrates the use of Java Persistence API (JPA) to manage various types of relationships between entities in a relational database. The project includes examples of one-to-one, one-to-many, and many-to-many relationships, showcasing how to persist, update, and delete entities while maintaining the integrity of their relationships using JUnit 5 for testing.

## Project Structure

- **src/main/java/com/khabir/model**: Contains the entity classes (`Student` and `Course`) with different types of relationships.
- **src/test/java/com/khabir/model/onetomany/bi**: Contains the test class for one-to-many bi-directional relationships.
- **src/test/java/com/khabir/model/manytomany/bi**: Contains the test class for many-to-many bi-directional relationships.
- **src/test/java/com/khabir/model/onetoone**: Contains the test class for one-to-one uni-directional relationships.

## Entity Classes

### Student

The `Student` entity represents a student with a unique ID and a name. It has relationships with the `Course` entity.

### Course

The `Course` entity represents a course with a unique ID and a name. It has relationships with the `Student` entity.

## Relationship Types

### One-to-One

The one-to-one relationship is demonstrated between `Student` and `Course` entities. Each student is associated with one course, and each course is associated with one student.

### One-to-Many

The one-to-many relationship is demonstrated between `Student` and `Course` entities. Each student can be associated with multiple courses, but each course is associated with only one student.

### Many-to-Many

The many-to-many relationship is demonstrated between `Student` and `Course` entities. Each student can be associated with multiple courses, and each course can be associated with multiple students.

## Running the Tests

The project uses JUnit 5 for testing the functionality of the relationships. To run the tests, you can use the following command:

## Example Test Classes

### OneToOneUniTest

This test class demonstrates the one-to-one uni-directional relationship between Student and Course entities. It includes tests for creating, updating, and deleting entities while maintaining the relationship.

### OneToManyBiTest

This test class demonstrates the one-to-many bi-directional relationship between Student and Course entities. It includes tests for creating, updating, and deleting entities while maintaining the relationship.

### ManyToManyBiTest

This test class demonstrates the many-to-many bi-directional relationship between Student and Course entities. It includes tests for creating, updating, and deleting entities while maintaining the relationship.

## Dependencies

- JPA (Jakarta Persistence API)
- EclipseLink (JPA implementation)
- PostgreSQL (database) or any other database
- JUnit 5 (testing framework)

## Database Configuration

You need to fill in the db.properties file located in the resources directory with your database connection details. Here is an example:
```properties
DB_URL=jdbc:postgresql://localhost:5432/schooldb
DB_USER=yourusername
DB_PASSWORD=yourpassword
DB_DRIVER=org.postgresql.Driver
```

You can change the database to whatever you want by modifying the db.properties file. But, for this project, I used PostgreSQL. You can use MySQL, Oracle, or any other database by changing the driver and connection URL in the db.properties file. But, you need to add the corresponding database dependency in the pom.xml file.

## Getting Started

Move to the relationship test directory and run the test classes to see the results.
