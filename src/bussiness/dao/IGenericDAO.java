package bussiness.dao;

import java.util.List;

public interface IGenericDAO<T,E> extends IBasicDAO<T> {
    void save (T t); // cả logic them mới và chỉnh sửa
    void deleteById (E id);
    T findById (E id);
    List<T> executeQueryWithParams(String sql, Object[] params);
}
