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
    public Customer getById(Integer id) {
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

    @Override
    public boolean isPhoneNumberExist(String phoneNumber) {
        List<Customer> list = getAll();
        for (Customer customer : list) {
            if (customer.getPhone().equals(phoneNumber)) {
                return true; // số điện thoại đã tồn tại trong csdl
            }
        }
        return false;
    }

    @Override
    public boolean isEmailExist(String email) {
        List<Customer> list = getAll();
        for (Customer customer : list) {
            if (customer.getEmail().equals(email)) {
                return true; // email đã tồn tại trong csdl
            }
        }
        return false;
    }
}
