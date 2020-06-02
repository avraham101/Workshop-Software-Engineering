package Persitent.DaoProxy;

import DataAPI.PermissionType;
import Domain.Permission;
import Domain.Subscribe;
import Persitent.Dao.PermissionDao;
import Persitent.DaoInterfaces.IPermissionDao;

public class PermissionDaoProxy implements IPermissionDao {

    private PermissionDao dao;

    public PermissionDaoProxy(){
        this.dao = new PermissionDao();
    }

    @Override
    public boolean addPermission(Permission permission) {
        try{
            return dao.addPermission(permission);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removePermissionFromSubscribe(Permission perToDelete) {
        try{
            return dao.removePermissionFromSubscribe(perToDelete);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Permission findPermission(Permission perToFind) {
        try{
            return dao.findPermission(perToFind);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean addPermissionType(String storeName, String owner, PermissionType type) {
        try{
            return dao.addPermissionType(storeName,owner,type);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void removePermissionFromSubscribe(Permission p, Subscribe subscribe) {
        dao.removePermissionFromSubscribe(p,subscribe);
    }

    @Override
    public boolean deletePermissionType(String storeName, String owner, PermissionType type) {
        try{
            return dao.deletePermissionType(storeName,owner,type);
        }catch (Exception e) {
            return false;
        }
    }
}
