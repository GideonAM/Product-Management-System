package com.amalitech.product_management_system.authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.login(loginDto));
    }

    @PostMapping("/activate-account")
    public ResponseEntity<String> activateAccount(@RequestParam(name = "token") String token) {
        return ResponseEntity.ok(authenticationService.activateAccount(token));
    }

}
