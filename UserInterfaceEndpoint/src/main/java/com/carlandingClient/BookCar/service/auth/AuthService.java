package com.carlandingClient.BookCar.service.auth;

import com.carlandingClient.BookCar.dto.LoginResponseDTO;
import com.carlandingClient.BookCar.dto.SignUpDTO;
import com.carlandingClient.BookCar.repo.AppUserRepo;
import com.carlandingClient.BookCar.entity.AppUsers;
import com.carlandingClient.BookCar.entity.user_detail_impl.UserPrinciple;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepo appUserRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers appUser = appUserRepo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException("User with Email : " + username + " not found"));

        return new UserPrinciple(appUser);
    }

    public AppUsers findUserByEmail(String email){
        return appUserRepo.findByEmailIgnoreCase(email)
                .orElseThrow(()-> new BadCredentialsException("User with email : " + email + " not found")) ;
    }

    @Transactional(rollbackFor = {Exception.class , RuntimeException.class})
    public LoginResponseDTO signUp(SignUpDTO signUpDTO){

        AppUsers user = appUserRepo.findByEmailIgnoreCase(signUpDTO.getEmail())
                .orElse(null);

        if (user != null){
            throw new BadCredentialsException("Email already present " + signUpDTO.getEmail());
        }

        AppUsers toBeCreatedUser = returnAppUserEntity(signUpDTO);

        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        AppUsers savingUser = appUserRepo.save(toBeCreatedUser);

        String token = jwtService.generateAccessToken(savingUser);

        return new LoginResponseDTO(token);
    }

    private AppUsers returnAppUserEntity(SignUpDTO signUpDTO){
        return new AppUsers(signUpDTO.getFullName() , signUpDTO.getEmail(), signUpDTO.getPassword(), signUpDTO.getRole());
    }
}
