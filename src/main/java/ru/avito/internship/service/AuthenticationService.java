package ru.avito.internship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avito.internship.domain.dto.JwtAuthenticationResponse;
import ru.avito.internship.domain.dto.SignInRequest;
import ru.avito.internship.domain.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        User user;
        if (userService.existsByUsername(request.username())) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            ));

            user = (User) userService
                    .userDetailsService()
                    .loadUserByUsername(request.username());
        } else {
            user = User.builder()
                    .username(request.username())
                    .password(passwordEncoder.encode(request.password()))
                    .balance(1000)
                    .build();

            userService.save(user);
        }
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
