package Persitent;

import Domain.*;

import javax.persistence.*;

public class CartDao extends Dao<Cart> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");

//    public static void main(String[] args){
//        PermissionType[] ps = {PermissionType.OWNER};
//        List<PermissionType> perms = new ArrayList<PermissionType>(Arrays.asList(ps));
//        Permission p = new Permission(new Subscribe("roy","roy"), new HashSet<>(perms));
//        Basket b = new Basket(new Store("roy",p,"roy"),"roy");
//        ProductData pd  = new ProductData("roy","roy","roy",null,0,0,PurchaseTypeData.IMMEDDIATE);
//        Product pr = new Product(pd,new Category("roy"));
//        b.addProduct(pr,0);
//        Cart c = new Cart("roy");
//        c.getBaskets().put("roy",b);
//        Cart c2 = new Cart("yuval");
//        BasketDao bdao = new BasketDao();
////        bdao.addBasket(c);
//        bdao.removeBasket(c);
//        ENTITY_MANAGER_FACTORY.close();
//    }

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
}
