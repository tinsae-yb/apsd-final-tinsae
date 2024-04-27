package org.example.crms.config;


import lombok.RequiredArgsConstructor;
import org.example.crms.entity.Role;
import org.example.crms.filter.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception
    {

        http.csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        {
                            // AUTH
                            authorizeRequests.requestMatchers(HttpMethod.POST, "api/v1/auth/login", "api/v1/auth/refresh", "api/v1/auth/register").permitAll();
                           // VEHICLE
                            authorizeRequests.requestMatchers(HttpMethod.POST, "api/v1/vehicle").hasAuthority(Role.RoleType.ADMIN.name());
                            authorizeRequests.requestMatchers(HttpMethod.GET, "api/v1/vehicle").permitAll();
                            authorizeRequests.requestMatchers(HttpMethod.PUT, "api/v1/vehicle/{id}").hasAuthority(Role.RoleType.ADMIN.name());
                            // LOCATION
                            authorizeRequests.requestMatchers(HttpMethod.POST, "api/v1/location").hasAuthority(Role.RoleType.ADMIN.name());
                            authorizeRequests.requestMatchers(HttpMethod.GET, "api/v1/location").permitAll();
                            // RESERVATION
                            authorizeRequests.requestMatchers(HttpMethod.POST, "api/v1/reservation").authenticated();
                            authorizeRequests.requestMatchers(HttpMethod.PUT, "api/v1/reservation/{id}").authenticated();
                            authorizeRequests.requestMatchers(HttpMethod.GET, "api/v1/reservation").authenticated();
                            authorizeRequests.requestMatchers(HttpMethod.POST, "api/v1/reservation/{id}/make-payment").authenticated();

                            // USER

                            authorizeRequests.requestMatchers(HttpMethod.GET, "api/v1/customer").hasAuthority(Role.RoleType.ADMIN.name());
                            authorizeRequests.requestMatchers(HttpMethod.GET, "api/v1/customer/profile").hasAuthority(Role.RoleType.CUSTOMER.name());

                        }
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return  http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
