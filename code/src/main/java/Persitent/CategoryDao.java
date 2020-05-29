package Persitent;

import Domain.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class CategoryDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public Category find(String name){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Category cat = null;
        try {
            cat=em.find(Category.class,name);
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return cat;
    }
}
