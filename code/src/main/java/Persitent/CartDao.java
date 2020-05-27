package Persitent;

import Domain.*;

import javax.persistence.*;
import javax.transaction.Transactional;

public class CartDao extends Dao<Cart> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

    public boolean add(Cart cart){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        boolean output=false;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(cart);
            et.commit();
            output=true;
        }
        catch (Exception e){
            if(et != null)
                et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        return output;
    }

    public void remove(Cart cart){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try{
            et = em.getTransaction();
            et.begin();
            Cart c = em.find(Cart.class, cart.getBuyer());
            if(c!=null) {

                em.createNativeQuery("DELETE FROM basket WHERE username=?")
                        .setParameter(1, cart.getBuyer())
                        .executeUpdate();
                em.createNativeQuery("DELETE FROM cart WHERE username=?")
                        .setParameter(1,cart.getBuyer())
                        .executeUpdate();
                //em.remove(em.contains(c) ? c : em.merge(c));
                et.commit();
            }


//            int x=  em.createNativeQuery("DELETE FROM product WHERE storeName=? AND productName=?")
//                    .setParameter(1, product.getStore())
//                    .setParameter(2, product.getName())
//                    .executeUpdate();
//            et.commit();
//            return x > 0;
        } catch (Exception e){
            if(et!=null)
                et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }


    public boolean update(Cart info) {
        return super.update(ENTITY_MANAGER_FACTORY.createEntityManager(), info);
    }

    public Cart find(String name){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Cart cart = null;
        try {
            cart=em.find(Cart.class,name);
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        return cart;
    }
    @Transactional
    public boolean replaceCart(Cart cart) {
       remove(cart);
       return add(cart);
    }
}
