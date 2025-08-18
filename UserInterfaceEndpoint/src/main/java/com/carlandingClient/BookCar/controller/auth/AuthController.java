package com.carlandingClient.BookCar.controller.auth;

import com.carlandingClient.BookCar.dto.LoginDTO;
import com.carlandingClient.BookCar.dto.LoginResponseDTO;
import com.carlandingClient.BookCar.dto.SignUpDTO;
import com.carlandingClient.BookCar.service.auth.AuthLoginService;
import com.carlandingClient.BookCar.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthLoginService authLoginService;

    public AuthController(AuthService authService, AuthLoginService authLoginService) {
        this.authService = authService;
        this.authLoginService = authLoginService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<LoginResponseDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        LoginResponseDTO loginResponseDTO = authService.signUp(signUpDTO);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = authLoginService.login(loginDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
