package bussiness.service;

import java.util.List;

public interface IGenericService<T,E> {
    void addMultiple(List<T> list);
    List<T> getAll ();
    void update (T t);
    T getById(int id);
    void deleteById(int id);
}
