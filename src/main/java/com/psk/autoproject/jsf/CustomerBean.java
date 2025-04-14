package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.service.CarService;
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

    @Inject
    private CarService carService;

    private List<Customer> customers;
    private Customer newCustomer = new Customer();
    private Long selectedCustomerId;
    private Long selectedCarId;

    @PostConstruct
    public void init() {
        customers = customerService.findAllWithCars();
    }

    public String saveCustomer() {
        customerService.save(newCustomer);
        newCustomer = new Customer();
        customers = customerService.findAllWithCars();
        return null;
    }

    public String assignCarToCustomer() {
        if (selectedCustomerId == null || selectedCarId == null) {
            return null;
        }
        try {
            Customer customer = customerService.findById(selectedCustomerId);
            Car car = carService.findByIdWithOwners(selectedCarId);

            if (car != null && customer != null) {
                car.getOwners().add(customer);
                carService.save(car);
                customers = customerService.findAllWithCars();
                selectedCustomerId = null;
                selectedCarId = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Car> getAvailableCars() {
        return carService.findAll();
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

    public Long getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(Long selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

    public Long getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(Long selectedCarId) {
        this.selectedCarId = selectedCarId;
    }
}
