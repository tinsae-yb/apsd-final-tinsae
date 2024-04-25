package org.example.crms.dto.payment;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRequest {

    // card info

    @NotBlank
    private String cardNumber;
    @NotBlank
    private String cardHolderName;
    @NotBlank
    private String expiryDate;
    @NotBlank
    private String cvv;




}
