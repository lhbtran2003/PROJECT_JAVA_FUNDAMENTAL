package bussiness.service.customer;

import bussiness.service.IGenericService;
import entity.Customer;

import java.util.List;

public interface ICustomerService extends IGenericService<Customer, Integer> {
    List<Customer> getCustomerByName(String name);
}
