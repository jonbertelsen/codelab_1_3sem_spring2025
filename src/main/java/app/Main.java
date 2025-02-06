package app;

import app.config.HibernateConfig;
import app.daos.CourseDAO;
import app.daos.PersonDAO;
import app.daos.StudentDAO;
import app.entities.Course;
import app.entities.Person;
import app.entities.Student;
import app.enums.StudentStatus;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final PersonDAO personDAO = PersonDAO.getInstance(emf);

    private static final StudentDAO studentDAO = StudentDAO.getInstance(emf);
    private static final CourseDAO courseDAO = CourseDAO.getInstance(emf);

    public static void main(String[] args) {

        try {
            Person person = Person.builder()

                      .name("John Snow")
                      .age(30)
                      .build();
            person = personDAO.createPerson(person);
            System.out.println(person);
            runTasks();
        } catch (ApiException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    private static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }

    private static void runTasks(){
        // 1. Create new student
        Student student = Student.builder()
                                 .name("Alice Johnson")
                                 .age(22)
                                 .phone("123-456-7890")
                                 .courseId(1L)
                                 .email("alice@example.com")
                                 .status(StudentStatus.ACTIVE) // Assuming StudentStatus is an enum
                                 .enrollmentDate(LocalDate.of(2023, 9, 1))
                                 .dateOfBirth(LocalDate.of(2001, 5, 15))
                                 .build();
        student = studentDAO.createStudent(student);
        System.out.println("1. Created student" + student);

        student = Student.builder()
                                 .name("John Snow")
                                 .age(30)
                                 .phone("435-111-6545")
                                 .courseId(1L)
                                 .email("john@wintherfell.com")
                                 .status(StudentStatus.ACTIVE) // Assuming StudentStatus is an enum
                                 .enrollmentDate(LocalDate.of(2024, 9, 1))
                                 .dateOfBirth(LocalDate.of(1995, 6, 23))
                                 .build();
        student = studentDAO.createStudent(student);
        System.out.println("1. Created another student" + student);


        // 2. Create new course
        Course course = Course.builder()
              .teacher("Dr. Smith")
              .classroom("Room 101")
              .time(LocalTime.of(10, 30)) // 10:30 AM
              .topic("Introduction to Java")
              .build();
        courseDAO.createCourse(course);
        System.out.println("2. Created course" + course);

        // 3. Update student information
        student = Student.builder()
             .id(1L)
             .name(student.getName())
             .age(25)
             .phone("123-456-7890")
             .courseId(2L)
             .email("alice@example.com")
             .status(StudentStatus.INACTIVE)
             .enrollmentDate(LocalDate.of(2023, 9, 1))
             .dateOfBirth(LocalDate.of(2001, 5, 15))
             .build();
        student = studentDAO.updateStudent(student);
        System.out.println("3. Updated student" + student);

        // 4. Update course information

        // 5. Delete student

       // studentDAO.removeStudent(1L);
        System.out.println("5. Deleted student");

        // 6. Delete course

        // 7. List all students
        List<Student> students = studentDAO.getAllStudents();
        students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .forEach(System.out::println);

        // 8. List all courses

        // 9. List all courses for a specific student

        // 10. List all students for a specific course (use streams and filters for this one)

        System.out.println("10. List all students for a specific course (id = 1)");
        students = studentDAO.getAllStudents();
        students.stream()
                .filter(s -> s.getCourseId() == 1L)
                .forEach(System.out::println);

        // 11. Update student name

        Student studentWithNewName = studentDAO.updateStudentName(student.getId(), "Sansa Stark");
        System.out.println("Update name: " + studentWithNewName);

    }

}