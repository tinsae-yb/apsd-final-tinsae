package org.example.crms.controllers;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.user.BasicCustomerInformation;
import org.example.crms.dto.user.CustomerResponse;
import org.example.crms.service.CustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {


   private final CustomerService customerService;


    @GetMapping
    public List<BasicCustomerInformation> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/profile")
    public CustomerResponse getProfile(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return customerService.getProfile(userDetails);
    }


}
