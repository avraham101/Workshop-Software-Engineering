package Persitent.DaoInterfaces;

import Domain.Review;

public interface IReviewDao {
    boolean addReview(Review review);
    Review find(int id);
    boolean remove(int id);
}
