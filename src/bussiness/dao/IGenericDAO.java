package bussiness.dao;

import java.util.List;

public interface IGenericDAO<T,E> {
    List<T> findAll ();
    void save (T t); // cả logic them mới và chỉnh sửa
    void deleteById (E id);
    T findById (E id);

}
