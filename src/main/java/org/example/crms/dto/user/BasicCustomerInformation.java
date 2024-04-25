package org.example.crms.dto.user;


import lombok.Data;
import org.example.crms.entity.Customer;

@Data
public class BasicCustomerInformation {
    private Long id;
    private String username;
    private String email;

    public static BasicCustomerInformation fromCustomer(Customer customer) {
        BasicCustomerInformation basicCustomerInformation = new BasicCustomerInformation();
        basicCustomerInformation.username = customer.getUsername();
        basicCustomerInformation.email = customer.getEmail();
        basicCustomerInformation.id = customer.getId();
        return basicCustomerInformation;
    }
}
