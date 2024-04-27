package org.example.crms.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.crms.dto.user.BasicCustomerInformation;
import org.example.crms.dto.user.CustomerResponse;
import org.example.crms.exception.NotFoundException;
import org.example.crms.repo.CustomerRepository;
import org.example.crms.service.CustomerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public List<BasicCustomerInformation> getAllCustomers() {
        return customerRepository.findAll().stream().map(BasicCustomerInformation::fromCustomer).toList();
    }

    @Override
    public CustomerResponse getProfile(UserDetails userDetails) {
        return customerRepository.findByEmail(userDetails.getUsername())
                .map(CustomerResponse::fromCustomer)
                .orElseThrow(
                        ()->new  NotFoundException("Customer not found")
                );
    }
}
