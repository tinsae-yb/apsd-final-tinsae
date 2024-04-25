package org.example.crms.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.crms.UserDetailsImpl;
import org.example.crms.dto.RoleDTO;
import org.example.crms.dto.auth.LoginRequest;
import org.example.crms.dto.auth.LoginResponse;
import org.example.crms.dto.auth.RefreshTokenRequest;
import org.example.crms.dto.auth.RefreshTokenResponse;
import org.example.crms.dto.user.RegisterUserRequest;
import org.example.crms.dto.user.RegisterUserResponse;
import org.example.crms.entity.Customer;
import org.example.crms.entity.Role;
import org.example.crms.entity.User;
import org.example.crms.exception.BadRequestException;
import org.example.crms.repo.CustomerRepository;
import org.example.crms.repo.RoleRepository;
import org.example.crms.repo.UserRepository;
import org.example.crms.service.AuthService;
import org.example.crms.util.JWTUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {


        try {



            if(registerUserRequest.getRole().getRoleType() == Role.RoleType.CUSTOMER){
                Customer customer = registerUserRequest.toCustomer();

                customer.setPassword(passwordEncoder.encode(customer.getPassword()));
                List<Role> roles = customer.getRoles().stream().map(role -> {
                    Optional<Role> optionalRole = roleRepository.findByRoleType(role.getRoleType());
                    if (optionalRole.isEmpty()) {
                        role = roleRepository.save(role);
                    } else {
                        role = optionalRole.get();
                    }
                    return role;
                }).toList();
                customer.setRoles(roles);
                customer = customerRepository.save(customer);
                UserDetails userDetails = new UserDetailsImpl(customer);
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                String refreshToken = jwtUtil.generateRefreshToken(userDetails);
                return RegisterUserResponse.fromUser(customer, accessToken, refreshToken);

            }

            User user = registerUserRequest.toUser();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            List<Role> roles = user.getRoles().stream().map(role -> {
                Optional<Role> optionalRole = roleRepository.findByRoleType(role.getRoleType());
                if (optionalRole.isEmpty()) {
                    role = roleRepository.save(role);
                } else {
                    role = optionalRole.get();
                }
                return role;
            }).toList();
            user.setRoles(roles);
            user = userRepository.save(user);
            UserDetails userDetails = new UserDetailsImpl(user);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            return RegisterUserResponse.fromUser(user, accessToken, refreshToken);

        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("users_email_key")) {
                throw new BadRequestException("Email already exists");
            } else if (e.getMessage().contains("users_username_key")) {
                throw new BadRequestException("Username already exists");
            }
            throw new BadRequestException("Invalid data");
        }


    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid email or password");
        }


        UserDetails userDetails = new UserDetailsImpl(user);

        String accessToken = jwtUtil.generateAccessToken(userDetails);

        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new LoginResponse(accessToken, refreshToken);


    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {

       jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
       String subject = jwtUtil.extractClaim(refreshTokenRequest.getRefreshToken(), Claims::getSubject);
        User user = userRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("Invalid token"));
        UserDetails userDetails = new UserDetailsImpl(user);
        String accessToken = jwtUtil.generateAccessToken(userDetails);

        return new RefreshTokenResponse(accessToken);


    }
}
