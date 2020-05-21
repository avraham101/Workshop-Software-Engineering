package Persitent;

import DataAPI.PermissionType;
import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BasketDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public static void main(String[] args){
        PermissionType[] ps = {PermissionType.OWNER};
        List<PermissionType> perms = new ArrayList<PermissionType>(Arrays.asList(ps));
        Permission p = new Permission(new Subscribe("roy","roy"), new HashSet<>(perms));
        Basket b = new Basket(new Store("roy",p,"roy"),"roy");
        ProductData pd  = new ProductData("roy","roy","roy",null,0,0,PurchaseTypeData.IMMEDDIATE);
        Product pr = new Product(pd,new Category("roy"));
        b.addProduct(pr,0);
        Cart c = new Cart("roy");
        c.getBaskets().put("roy",b);
        Cart c2 = new Cart("yuval");
        BasketDao bdao = new BasketDao();
//        bdao.addBasket(c);
        bdao.removeBasket(c);
        ENTITY_MANAGER_FACTORY.close();
    }

    public void addBasket(Cart cart){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(cart);
            et.commit();
        }
        catch (Exception e){
            if(et != null)
                et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public void removeBasket(Cart cart){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try{
            et = em.getTransaction();
            et.begin();
            Cart c = em.find(Cart.class, cart.getBuyer());
            em.remove(em.contains(c) ? c : em.merge(c));
            et.commit();
        } catch (Exception e){
            if(et!=null)
                et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }
}
