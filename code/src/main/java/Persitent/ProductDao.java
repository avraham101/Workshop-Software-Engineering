package Persitent;

import DataAPI.ProductData;
import Domain.PurchaseType;
import DataAPI.PurchaseTypeData;
import Domain.Category;
import Domain.Product;
import Domain.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.ArrayList;

public class ProductDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public static void main(String[] args){
//        addProduct(new Product(new ProductData("proc2","hanut4","cat",
//                new ArrayList<>(),3,5,PurchaseTypeData.IMMEDDIATE),new Category("cat")));
//        updateProduct("proc2","hanut4",
//                new Review("yuv","hanut4","proc2","hello2"));
//
//        updateProduct(new Product(new ProductData("proc2","hanut4","cat",
//                new ArrayList<>(),7,5,PurchaseTypeData.IMMEDDIATE),new Category("cat")));
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addProduct(Product product) {
        product.getReviews().add(new Review("yuv","hanut4","proc2","hello"));
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Save the object
            em.persist(product);
            et.commit();
        }
        //if the permission type already exists
        catch(javax.persistence.PersistenceException exp ){
            if(et.isActive())
                et.rollback();
            PurchaseType purchaseType = em.getReference(PurchaseType.class, product.getPurchaseType().getPurchaseTypeData());
            product.setPurchaseType(purchaseType);
            et = em.getTransaction();

            et.begin();

            // Save the object
            em.persist(product);
            et.commit();
        }
        catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public static void updateProduct(Product product){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            ArrayList<String > keys=new ArrayList<>();
            keys.add(product.getStore());
            keys.add(product.getName());
            Product p=em.find(Product.class,product);
            ArrayList<Review> l=new ArrayList<>();
            l.add(new Review("yuva","hanut4","proc2","hellohello"));
            p.getReviews().addAll(l);
            p.setAmount(5400);
            //em.merge(product);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }



}

class ProductKey implements Serializable {

    String store;
    String productName;

    public ProductKey(String store, String productName){
        this.productName=productName;
        this.store=store;
    }
    //Override hascode and equals
}
