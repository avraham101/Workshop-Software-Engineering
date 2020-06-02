package Persitent.Dao;

import Domain.Review;
import Persitent.DaoInterfaces.IReviewDao;

import javax.persistence.*;

public class ReviewDao extends Dao<Review> implements IReviewDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("product");


    public boolean addReview(Review review){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        return super.add(em,review);
    }

    public Review find(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Review review = null;
        try {
            review=em.find(Review.class,id);
        }
        catch(NoResultException ex) {
        }
        finally {
            em.close();
        }
        return review;

    }


    public boolean remove(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Review review = null;
        boolean output=false;

        try {
            et = em.getTransaction();
            et.begin();
            review=em.find(Review.class,id);
            em.remove(review);
            et.commit();
            output=true;
        }
        catch(NoResultException ex) {
            if (et != null) {
                et.rollback();
            }
            output=false;
        }
        finally {
            em.close();
        }
        return output;
    }
}
