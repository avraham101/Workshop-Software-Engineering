package Persitent.DaoInterfaces;

import DataAPI.PermissionType;
import Domain.Permission;
import Domain.Subscribe;

public interface IPermissionDao {
    boolean addPermission(Permission permission);
    boolean removePermissionFromSubscribe(Permission perToDelete, boolean toClose, boolean toOpen);
    Permission findPermission(Permission perToFind);
    boolean addPermissionType(String storeName, String owner, PermissionType type);
    void removePermissionFromSubscribe(Permission p, Subscribe subscribe);
    boolean deletePermissionType(String storeName, String owner, PermissionType type);

    boolean removePermissionFromSubscribe(Permission permission);
}
