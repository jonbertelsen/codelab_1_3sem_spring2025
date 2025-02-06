package app.daos;

import app.entities.Course;
import app.entities.Person;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CourseDAO {
    private static EntityManagerFactory emf;
    private static CourseDAO instance = null;

    private CourseDAO() {}

    public static CourseDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new CourseDAO();
        }
        return instance;
    }

    public Course createCourse(Course course) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(course);
                em.getTransaction().commit();
                return course;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating course", e);
            }
        }
    }

}
