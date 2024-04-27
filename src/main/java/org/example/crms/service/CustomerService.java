package org.example.crms.service;

import org.example.crms.dto.user.BasicCustomerInformation;
import org.example.crms.dto.user.CustomerResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<BasicCustomerInformation> getAllCustomers();

    CustomerResponse getProfile(UserDetails userDetails);
}
