package org.example.crms.dto.user;

import lombok.Data;
import org.example.crms.dto.RoleDTO;
import org.example.crms.entity.User;

import java.util.List;

@Data
public class RegisterUserResponse {

    private String username;

    private String email;

    private List<RoleDTO> roles;

    private String accessToken;

    private String refreshToken;

    public static RegisterUserResponse fromUser(User user, String accessToken, String refreshToken) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.username = user.getUsername();
        registerUserResponse.email = user.getEmail();
        registerUserResponse.roles = user.getRoles().stream().map(RoleDTO::fromRole).toList();
        registerUserResponse.accessToken = accessToken;
        registerUserResponse.refreshToken = refreshToken;
        return registerUserResponse;
    }
}
