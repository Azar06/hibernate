package com.valtech.tp.app1.ws.customer;

import com.valtech.tp.app1.domain.model.customer.Customer;
import com.valtech.tp.app1.domain.service.customer.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ApiOperation("Get a list of customer")
    public List<Customer> getCustomers() {
        return customerService.findCustomers();
    }
}
