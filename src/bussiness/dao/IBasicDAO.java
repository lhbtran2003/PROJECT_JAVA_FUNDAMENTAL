package bussiness.dao;

import java.util.List;

public interface IBasicDAO<T> {
    List<T> findAll ();
}
