package com.amalitech.product_management_system.authentication;

import com.amalitech.product_management_system.security.JwtService;
import com.amalitech.product_management_system.user.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    @Transactional
    public String register(@Valid RegisterDto registerDto) {
        User newUser = User.builder()
                .email(registerDto.email())
                .password(passwordEncoder.encode(registerDto.password()))
                .role(Role.USER)
                .build();
        User user = userRepository.save(newUser);

        String activationToken = generateVerificationToken(user);
        UserToken userToken = UserToken.builder()
                .token(activationToken)
                .user(user)
                .build();
        userTokenRepository.save(userToken);

        return "Account verification token sent: " + activationToken;
    }

    public String login(@Valid LoginDto loginDto) {
        Authentication authenticated = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );

        User user = (User) authenticated.getPrincipal();
        String jwt = jwtService.generateToken(new HashMap<>(), user.getEmail());

        UserToken userToken = UserToken.builder()
                .token(jwt)
                .user(user)
                .build();
        userTokenRepository.save(userToken);

        return jwt;
    }

    private String generateVerificationToken(User user) {
        var numbers = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(numbers.length());
            builder.append(numbers.charAt(index));
        }

        return builder.toString();
    }

    @Transactional
    public String activateAccount(String token) {
        UserToken userToken = userTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid verification token"));

        User user = userToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        userTokenRepository.delete(userToken);
        return "Account verification successful";
    }
}
