package Persitent.DaoInterfaces;

import Domain.Request;

public interface IRequestDao {
    boolean addRequest(Request request);
    Request find(int id);
    boolean removeRequest(int id);
    boolean update(Request request);
}
