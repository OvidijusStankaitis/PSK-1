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
import java.util.HashSet;
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
    private Customer selectedCustomer;
    private Long selectedCustomerId;
    private Long selectedCarId;

    @PostConstruct
    public void init() {
        // Use the eager method if you need to show the cars on the page.
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
            // Load the customer with its cars eagerly.
            Customer customer = customerService.findByIdWithCars(selectedCustomerId);
            // Load the car (if needed, you could also add a similar eager method for Car if you plan to display its relationships)
            Car car = carService.findById(selectedCarId);

            if (customer != null && car != null) {
                if (customer.getCars() == null) {
                    customer.setCars(new HashSet<>());
                }
                customer.getCars().add(car);

                // Save the owning side (Customer) so the join table is updated.
                customerService.save(customer);

                // Refresh the customer list with their cars eagerly.
                customers = customerService.findAllWithCars();

                // Reset selections.
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

    // Getters & Setters
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