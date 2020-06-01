package Persitent.DaoProxy;

import Domain.Request;
import Persitent.Dao.RequestDao;
import Persitent.DaoInterfaces.IRequestDao;

public class RequestDaoProxy implements IRequestDao {

    private RequestDao dao;

    public RequestDaoProxy(){
        this.dao = new RequestDao();
    }

    @Override
    public boolean addRequest(Request request) {
        try{
            return dao.addRequest(request);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Request find(int id) {
        try{
            return find(id);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeRequest(int id) {
        try{
            return dao.removeRequest(id);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Request request) {
        try{
            return dao.update(request);
        }catch (Exception e) {
            return false;
        }
    }
}
