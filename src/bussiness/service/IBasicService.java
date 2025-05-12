package bussiness.service;

import java.util.List;

public interface IBasicService<T>{
    void addMultiple(List<T> list);
    List<T> getAll ();
}
