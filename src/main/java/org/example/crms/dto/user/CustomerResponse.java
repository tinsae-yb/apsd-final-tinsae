package org.example.crms.dto.user;

import lombok.Data;
import org.example.crms.dto.reservation.ReservationResponse;
import org.example.crms.entity.Customer;

import java.util.List;

@Data
public class CustomerResponse {
    private Long id;
    private String username;
    private String email;

    private List<ReservationResponse> reservations;

    public static CustomerResponse fromCustomer(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.username = customer.getUsername();
        customerResponse.email = customer.getEmail();
        customerResponse.id = customer.getId();
        customerResponse.reservations = customer.getReservations().stream().map(ReservationResponse::fromReservation).toList();
        return customerResponse;
    }
}
