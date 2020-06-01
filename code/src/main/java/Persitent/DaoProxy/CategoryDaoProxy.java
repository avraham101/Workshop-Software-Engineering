package Persitent.DaoProxy;

import Domain.Category;
import Persitent.Dao.CategoryDao;
import Persitent.DaoInterfaces.ICategoryDao;

public class CategoryDaoProxy implements ICategoryDao {

    private ICategoryDao dao;

    public CategoryDaoProxy(){
        dao = new CategoryDao();
    }

    @Override
    public Category find(String name) {
        try{
            return dao.find(name);
        }catch (Exception e) {
            return null;
        }
    }
}
