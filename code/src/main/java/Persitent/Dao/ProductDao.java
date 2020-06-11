package Persitent.Dao;

import Domain.PurchaseType;
import Domain.Category;
import Domain.Product;
import Persitent.DaoInterfaces.ICategoryDao;
import Persitent.DaoInterfaces.IProductDao;
import Persitent.DaoInterfaces.IPurchaseTypeDao;
import Persitent.DaoProxy.PurchaseTypeDaoProxy;
import Utils.Utils;

import javax.persistence.*;

public class ProductDao implements IProductDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory(Utils.DB);
    private ICategoryDao categoryDao;
    private IPurchaseTypeDao purchaseTypeDao;

    public ProductDao(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        this.purchaseTypeDao = new PurchaseTypeDaoProxy();
    }

    public boolean addProduct(Product product) {
        boolean hasCat=categoryDao.find(product.getCategory().getName())!=null;
        boolean hasPurchase=purchaseTypeDao.find(product.getPurchaseType())!=null;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
        boolean output=false;

        try {
            if(hasPurchase){
                PurchaseType purchaseType = em.getReference(PurchaseType.class, product.getPurchaseType().getPurchaseTypeData());
                product.setPurchaseType(purchaseType);
            }
            if(hasCat){
                Category cat=em.getReference(Category.class, product.getCategory().getName());
                product.setCategory(cat);
            }
            // Get transaction and start
            et = em.getTransaction();
            et.begin();


            // Save the object
            em.persist(product);
            et.commit();
            output = true;
        }
        catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            //ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
            return output;
        }
    }

    public boolean updateProduct(Product product){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(product);
            et.commit();
            output= true;
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
//            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }

    public boolean removeProduct(Product product){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean output=false;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
//            Product p = em.find(Product.class,product);
//            em.remove(p);
            int x=  em.createNativeQuery("DELETE FROM product WHERE storeName=? AND productName=?")
                    .setParameter(1, product.getStore())
                    .setParameter(2, product.getName())
                    .executeUpdate();
            et.commit();
            output= x > 0;
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
        } finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }

    public Product find(Product keys){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Product product = null;
        try {
            product=em.find(Product.class,keys);
        }
        catch(NoResultException ex) {
        }
        finally {
            em.close();
        }
        return product;
    }


    public boolean removeProduct(String productName, String storeName) {
        return removeProduct(new Product(productName,storeName));
    }
}
