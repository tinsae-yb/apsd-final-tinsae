package org.example.crms.dto.user;

import lombok.Data;
import org.example.crms.dto.RoleDTO;
import org.example.crms.entity.User;

import java.util.List;

@Data
public class RegisterUserResponse {
    private Long id;

    private String username;

    private String email;

    private List<RoleDTO> roles;

    private String accessToken;

    private String refreshToken;

    public static RegisterUserResponse fromUser(User user, String accessToken, String refreshToken) {
        RegisterUserResponse userResponse = new RegisterUserResponse();
        userResponse.id = user.getId();
        userResponse.username = user.getUsername();
        userResponse.email = user.getEmail();
        userResponse.roles = user.getRoles().stream().map(RoleDTO::fromRole).toList();
        userResponse.accessToken = accessToken;
        userResponse.refreshToken = refreshToken;
        return userResponse;
    }
}
