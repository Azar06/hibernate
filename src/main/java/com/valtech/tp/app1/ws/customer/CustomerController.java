package com.valtech.tp.app1.ws.customer;

import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.service.customer.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ApiOperation("Get a list of customer")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    @ApiOperation("Insert a new customer")
    public ResponseEntity<Customer> insertCustomers(@RequestBody @Valid Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("The id has to be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insertCustomer(customer));
    }

    @GetMapping("{identifier:.+}")
    @ApiOperation("Find by id or email")
    public Customer findByIdentifier(@PathVariable("identifier") String identifier) {
        Long id = null;
        try {
            id = Long.valueOf(identifier);
        } catch (Exception ex) {
        }
        if (id == null) {
            return customerService.findCustomerByEmail(identifier);
        } else {
            return customerService.findCustomerById(id);
        }
    }

    @DeleteMapping("{identifier:.+}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Delete by id or email")
    public void deleteByIdentifier(@PathVariable("identifier") String identifier) {
        Long id = null;
        try {
            id = Long.valueOf(identifier);
        } catch (Exception ex) {
        }
        if (id == null) {
            customerService.deleteCustomerByEmail(identifier);
        } else {
            customerService.deleteCustomerById(id);
        }
    }
}
