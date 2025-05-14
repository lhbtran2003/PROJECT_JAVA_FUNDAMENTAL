package bussiness.service;

import java.util.List;

public interface IGenericService<T,E> extends IBasicService<T> {
    void update (T t);
    T getById(E id);
    void deleteById(int id);
}
