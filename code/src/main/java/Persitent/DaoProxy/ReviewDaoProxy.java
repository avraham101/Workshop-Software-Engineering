package Persitent.DaoProxy;

import Domain.Review;
import Persitent.Dao.ReviewDao;
import Persitent.DaoInterfaces.IReviewDao;

public class ReviewDaoProxy implements IReviewDao {

    private ReviewDao dao;

    public ReviewDaoProxy(){
        this.dao = new ReviewDao();
    }

    @Override
    public boolean addReview(Review review) {
        try{
            return dao.addReview(review);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Review find(int id) {
        try{
            return dao.find(id);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(int id) {
        try{
            return dao.remove(id);
        }catch (Exception e) {
            return false;
        }
    }
}
