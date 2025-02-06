package app.daos;

import app.entities.Person;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class PersonDAO {

    private static EntityManagerFactory emf;
    private static PersonDAO instance = null;

    private PersonDAO() {}

    public static PersonDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new PersonDAO();
        }
        return instance;
    }

    public Person createPerson(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                em.persist(person);
                em.getTransaction().commit();
                return person;
            } catch (Exception e) {
                throw new ApiException(401, "Error creating person", e);
        }
    }


}
