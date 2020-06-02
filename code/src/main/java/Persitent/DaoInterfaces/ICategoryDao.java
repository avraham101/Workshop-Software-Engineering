package Persitent.DaoInterfaces;

import Domain.Category;

public interface ICategoryDao {
    Category find(String name);

    boolean add(Category newCategory);
}
