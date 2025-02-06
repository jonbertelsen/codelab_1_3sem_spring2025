package app.daos;

import app.config.HibernateConfig;
import app.entities.Student;
import app.enums.StudentStatus;
import app.populators.StudentPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final StudentDAO studentDAO = StudentDAO.getInstance(emf);
    private static Student s1;
    private static Student s2;

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
                em.createQuery("DELETE FROM Student").executeUpdate();
                em.createNativeQuery("ALTER SEQUENCE student_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            Student[] students = StudentPopulator.populate(studentDAO);
            s1 = students[0];
            s2 = students[1];
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }

    @Test
    void getInstance() {
        assertNotNull(emf);
    }

    @Test
    void createStudent() {
        Student s3 = Student.builder()
                            .name("Cercei Lanister")
                            .age(35)
                            .phone("656-456-7690")
                            .courseId(1L)
                            .email("cercei@kingslanding.com")
                            .status(StudentStatus.ACTIVE)
                            .enrollmentDate(LocalDate.of(2023, 3, 11))
                            .dateOfBirth(LocalDate.of(1990, 5, 15))
                            .build();
        s3 = studentDAO.createStudent(s3);
        assertNotNull(s3.getId());
        List<Student> students = studentDAO.getAllStudents();
        assertEquals(3, students.size());
    }

    @Test
    void updateStudent() {
        Student s1ToUpdate = Student.builder()
                .id(s1.getId())
                .name("Arya Stark")
                .age(22)
                .phone("123-456-7890")
                .courseId(1L)
                .email("arya@winterfell.com")
                .status(StudentStatus.ACTIVE)
                .enrollmentDate(LocalDate.of(2023, 10, 1))
                .dateOfBirth(LocalDate.of(2010, 7, 17))
                .createdAt(s1.getCreatedAt())
                .build();
        s1ToUpdate = studentDAO.updateStudent(s1ToUpdate);
        s1 = studentDAO.getStudentById(s1ToUpdate.getId());
        assertThat(s1ToUpdate, samePropertyValuesAs(s1));
    }

    @Test
    void updateStudentName() {
        s1 = studentDAO.updateStudentName(s1.getId(), "Arya Stark");
        Student s3 = studentDAO.getStudentById(s1.getId());
        assertEquals("Arya Stark", s3.getName());
    }

    @Test
    void removeStudent() {
        studentDAO.removeStudent(s1.getId());
        List<Student> students = studentDAO.getAllStudents();
        assertEquals(1, students.size());
        assertEquals(s2.getId(), students.get(0).getId());
    }

    @Test
    void getAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        assertEquals(2, students.size());
    }
}