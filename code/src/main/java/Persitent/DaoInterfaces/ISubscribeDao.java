package Persitent.DaoInterfaces;

import Domain.Admin;
import Domain.Subscribe;

import java.util.List;

public interface ISubscribeDao {
    boolean addSubscribe(Subscribe subscribe);
    Subscribe find(String userName);
    void remove(String username);
    boolean update(Subscribe info);
    boolean logoutAll();
    List<Admin> getAllAdmins();
    List<String> getAllUserName();
}
