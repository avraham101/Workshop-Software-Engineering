package Persitent.Dao;

import Domain.Category;
import Persitent.DaoInterfaces.ICategoryDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CategoryDao extends Dao<Category> implements ICategoryDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean add(Category category) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,category);
    }


    public Category find(String name){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Category cat = null;
        try {
            cat=em.find(Category.class,name);
        }
        catch(Exception ex) {

        }
        finally {
            em.close();
        }
        return cat;
    }
}
