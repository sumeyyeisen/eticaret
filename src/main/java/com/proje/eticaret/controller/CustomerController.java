// src/main/java/com/proje/eticaret/controller/CustomerController.java

package com.proje.eticaret.controller;

import com.proje.eticaret.dto.CustomerDTO;
import com.proje.eticaret.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.addCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }
}
