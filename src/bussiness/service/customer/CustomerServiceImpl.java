package bussiness.service.customer;

import bussiness.dao.customer.CustomerDAOImpl;
import entity.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private CustomerDAOImpl customerDAOImpl;
    private static CustomerServiceImpl instance;

    public CustomerServiceImpl() {
        customerDAOImpl = CustomerDAOImpl.getInstance();
    }

    public static CustomerServiceImpl getInstance() {
        if (instance == null) {
            instance = new CustomerServiceImpl();
        }
        return instance;
    }


    @Override
    public void addMultiple(List<Customer> list) {
        for (Customer customer : list) {
            customerDAOImpl.save(customer);
        }
    }

    @Override
    public List<Customer> getAll() {

        return customerDAOImpl.findAll();
    }

    @Override
    public void update(Customer customer) {
        customerDAOImpl.save(customer);
    }

    @Override
    public Customer getById(int id) {
        return customerDAOImpl.findById(id);
    }

    @Override
    public void deleteById(int id) {
        customerDAOImpl.deleteById(id);
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        return customerDAOImpl.findCustomerByName(name);
    }
}
