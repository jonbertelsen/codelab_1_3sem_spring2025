package app.populators;

import app.daos.StudentDAO;
import app.entities.Student;
import app.enums.StudentStatus;

import java.time.LocalDate;

public class StudentPopulator {
    public static Student[] populate(StudentDAO studentDAO){
        Student s1 = Student.builder()
                                 .name("Sansa Stark")
                                 .age(22)
                                 .phone("123-456-7890")
                                 .courseId(1L)
                                 .email("sansa@winterfell.com")
                                 .status(StudentStatus.ACTIVE)
                                 .enrollmentDate(LocalDate.of(2023, 9, 1))
                                 .dateOfBirth(LocalDate.of(2001, 5, 15))
                                 .build();
        s1 = studentDAO.createStudent(s1);

        Student s2 = Student.builder()
                            .name("John Snow")
                            .age(28)
                            .phone("165-456-7456")
                            .courseId(1L)
                            .email("john@winterfell.com")
                            .status(StudentStatus.INACTIVE)
                            .enrollmentDate(LocalDate.of(2023, 10, 5))
                            .dateOfBirth(LocalDate.of(1997, 5, 15))
                            .build();
        s2 = studentDAO.createStudent(s2);
        return new Student[]{s1, s2};
    }
}
