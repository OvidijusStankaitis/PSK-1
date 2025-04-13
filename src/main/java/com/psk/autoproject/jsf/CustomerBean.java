package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CustomerService customerService;

    private List<Customer> customers;
    private Customer newCustomer = new Customer();
    private Customer selectedCustomer;

    @PostConstruct
    public void init() {
        customers = customerService.findAll();
    }

    public void saveCustomer() {
        customerService.save(newCustomer);
        newCustomer = new Customer();
        customers = customerService.findAll();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
