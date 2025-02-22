package Persitent.Dao;

import Domain.ProductInCart;
import Persitent.DaoInterfaces.IProductInCartDao;
import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProductInCartDao extends Dao<ProductInCart> implements IProductInCartDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);

    public boolean add(ProductInCart value) {
        return super.add(ENTITY_MANAGER_FACTORY.createEntityManager(), value);
    }

    public boolean update(ProductInCart info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }

    public boolean remove(ProductInCart value) {
        return super.remove(ENTITY_MANAGER_FACTORY.createEntityManager(), value);
    }

    public ProductInCart find( ProductInCart value) {
        return super.find(ENTITY_MANAGER_FACTORY.createEntityManager(), value);
    }

    public boolean remove(String product){
        EntityManager em=ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;
        try{
            et = em.getTransaction();
            et.begin();
            ProductInCart productInCart = em.find(ProductInCart.class,product);
            em.remove(em.contains(productInCart) ? productInCart : em.merge(productInCart));
            et.commit();
            output=true;
        } catch (Exception e){
            if(et!=null)
                et.rollback();
        }
        finally {
            em.close();
        }
        return output;
    }
}
