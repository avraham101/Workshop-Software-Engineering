package Persitent.DaoProxy;

import Domain.DayVisit;
import Persitent.Dao.VisitsPerDayDao;
import Persitent.DaoInterfaces.IVisitsPerDayDao;

import java.time.LocalDate;

public class VisitPerDayDaoProxy implements IVisitsPerDayDao {

    private VisitsPerDayDao dao;

    public VisitPerDayDaoProxy() {
        dao=new VisitsPerDayDao();
    }

    @Override
    public boolean add(DayVisit dayVisit) {
        try{
            return dao.add(dayVisit);
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
    public boolean update(DayVisit dayVisit) {
        try{
            return dao.update(dayVisit);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public DayVisit find(LocalDate date) {
        try{
            return dao.find(date);
        }catch (Exception e) {
            return null;
        }
    }
}
