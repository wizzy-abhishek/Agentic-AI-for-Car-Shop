package com.carlandingClient.BookCar.service;

import com.carlandingClient.BookCar.dto.LoginDTO;
import com.carlandingClient.BookCar.dto.LoginResponseDTO;
import com.carlandingClient.BookCar.entity.AppUsers;
import com.carlandingClient.BookCar.entity.user_detail_impl.UserPrinciple;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthLoginService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        AppUsers appUser = userPrincipal.getAppUsers();

        String token = jwtService.generateAccessToken(appUser);

        return new LoginResponseDTO(token);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

}
