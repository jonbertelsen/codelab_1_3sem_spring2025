package app.daos;

import app.entities.Student;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class StudentDAO {

    private static EntityManagerFactory emf;
    private static StudentDAO instance = null;

    private StudentDAO() {}

    public static StudentDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new StudentDAO();
        }
        return instance;
    }

    public Student createStudent(Student student) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
                return student;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating student", e);
            }
        }
    }

    public Student updateStudent(Student student) {
        try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                    Student updatedStudent = em.merge(student);
                em.getTransaction().commit();
                return updatedStudent;
            } catch (Exception e) {
                throw new ApiException(401, "Error updating student", e);
        }
    }

    // SLQ: update student set name = 'sansa stark' where id = 1

    public Student updateStudentName(Long id, String name) {
        try (EntityManager em = emf.createEntityManager()) {
            Student student = em.find(Student.class, id);
            if (student == null){
                throw new ApiException(401, "Student not found for update");
            }
            em.getTransaction().begin();
            student.setName(name);
            em.getTransaction().commit();
            return student;
        } catch (Exception e) {
            throw new ApiException(401, "Error updating student", e);
        }
    }

    public void removeStudent(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Student student = em.find(Student.class, id);
                if (student == null) {
                    throw new ApiException(404, "Student not found");
                }
                em.getTransaction().begin();
                em.remove(student);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error removing student", e);
            }
        }
    }

    public List<Student> getAllStudents() {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
                List<Student> students = query.getResultList();
                return students;
            } catch (Exception e) {
                throw new ApiException(401, "Error creating student", e);
            }
        }
    }





}
