package Persitent.DaoProxy;

import Domain.Revenue;
import Persitent.Dao.RevenueDao;
import Persitent.DaoInterfaces.IRevenueDao;

import java.time.LocalDate;

public class RevenueDaoProxy implements IRevenueDao {

    private RevenueDao dao;

    public RevenueDaoProxy(){
        this.dao = new RevenueDao();
    }

    @Override
    public boolean add(Revenue value) {
        try{
            return dao.add(value);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Revenue info) {
        try{
            return dao.update(info);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(LocalDate date) {
        try{
            return dao.remove(date);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Revenue find(LocalDate date) {
        try{
            return dao.find(date);
        }catch (Exception e) {
            return null;
        }
    }
}
