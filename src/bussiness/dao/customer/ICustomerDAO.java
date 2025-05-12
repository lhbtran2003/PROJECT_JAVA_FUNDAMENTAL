package bussiness.dao.customer;

import bussiness.dao.IGenericDAO;
import entity.Customer;

import java.util.List;

public interface ICustomerDAO extends IGenericDAO<Customer, Integer> {
    List<Customer> findCustomerByName(String name);
}
