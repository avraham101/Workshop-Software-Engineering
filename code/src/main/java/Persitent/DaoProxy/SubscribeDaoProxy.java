package Persitent.DaoProxy;

import Domain.Admin;
import Domain.Subscribe;
import Persitent.Dao.SubscribeDao;
import Persitent.DaoInterfaces.ISubscribeDao;

import java.util.List;

public class SubscribeDaoProxy implements ISubscribeDao {

    private SubscribeDao dao;

    public SubscribeDaoProxy(){
        this.dao = new SubscribeDao();
    }

    @Override
    public boolean addSubscribe(Subscribe subscribe) {
        try{
            return dao.addSubscribe(subscribe);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subscribe find(String userName) {
        try{
            return dao.find(userName);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(String username) {
        try{
            dao.remove(username);
        }catch (Exception e){}
    }

    @Override
    public boolean update(Subscribe info) {
        try{
            return dao.update(info);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        try{
            return dao.getAllAdmins();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<String> getAllUserName() {
        try{
            return dao.getAllUserName();
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean logoutAll(){
        try{
            return dao.logoutAll();
        }catch (Exception e){
            return false;
        }
    }
}
