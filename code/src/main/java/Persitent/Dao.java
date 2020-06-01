package Persitent;

import javax.persistence.*;

public class Dao<T> {

    public boolean add(EntityManager em, T value) {
        boolean output = false;
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            // Save the object
            em.persist(value);
            et.commit();
            output = true;
        }
        catch (Exception ex) {
            output = false;
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

    //need to be the same subscribe as the find
    public boolean update(EntityManager em,T info) {
        boolean output = false;
        EntityTransaction et = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
            em.merge(info);
            et.commit();
            output = true;
        }
        catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }

        }
        finally {
            // Close EntityManager
            em.close();
        }
        return output;
    }

    public boolean remove(EntityManager em, T value){
        EntityTransaction et = null;
        boolean output=false;
        try{
            et = em.getTransaction();
            et.begin();
            T val = (T) em.find(value.getClass(), value);
            em.remove(em.contains(val) ? val : em.merge(val));
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





    public T find(EntityManager em,T value){
        T val = null;
        try {
            val= (T) em.find(value.getClass(),value);
        }
        catch(NoResultException ex) {
        }
        finally {
            em.close();
        }
        return val;
    }

}
