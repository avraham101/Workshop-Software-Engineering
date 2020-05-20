package Persitent;

import Domain.PurchaseType;
import Domain.Category;
import Domain.Product;

import javax.persistence.*;

public class ProductDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");
    private CategoryDao categoryDao;
    private PurchaseTypeDao purchaseTypeDao;

    public ProductDao() {
        this.categoryDao = new CategoryDao();
        this.purchaseTypeDao = new PurchaseTypeDao();
    }

    //for test and expiriments
    public void main(){
//        addProduct(new Product(new ProductData("proc8","hanut","tko",
//                new ArrayList<>(),3,5,PurchaseTypeData.IMMEDDIATE),new Category("tko")));
        //Product p=categoryDao.find("tko");
        Product p=find(new Product("proc8","hanut"));
//        updateProduct("proc2","hanut4",
//                new Review("yuv","hanut4","proc2","hello2"));
//
//        updateProduct(new Product(new ProductData("proc2","hanut4","cat",
//                new ArrayList<>(),7,5,PurchaseTypeData.IMMEDDIATE),new Category("cat")));
        ENTITY_MANAGER_FACTORY.close();
    }

    public void addProduct(Product product) {
        boolean hasCat=categoryDao.find(product.getCategory().getName())!=null;
        boolean hasPurchase=purchaseTypeDao.find(product.getPurchaseType())!=null;
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

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
        }
    }

    public void updateProduct(Product product){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(product);
            et.commit();
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



    }

    public void removeProduct(Product product){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            Product p=em.find(Product.class,product);
            em.remove(product);
            et.commit();
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



    }

    public Product find(Product keys){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Product product = null;
        try {
            product=em.find(Product.class,keys);
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return product;
    }


}
