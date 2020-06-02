package Persitent.DaoProxy;

import Domain.OwnerAgreement;
import Persitent.Dao.OwnerAgreementDao;
import Persitent.DaoInterfaces.IOwnerAgreementDao;

public class OwnerAgreementDaoProxy implements IOwnerAgreementDao {

    private OwnerAgreementDao dao;

    public OwnerAgreementDaoProxy(){
        dao = new OwnerAgreementDao();
    }

    @Override
    public boolean add(OwnerAgreement value) {
        try{
            return dao.add(value);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(OwnerAgreement info) {
        try{
            return dao.update(info);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            return dao.remove(id);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public OwnerAgreement find(int id) {
        try{
            return dao.find(id);
        }catch (Exception e) {
            return null;
        }
    }
}
