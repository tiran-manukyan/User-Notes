package com.simple.spring.app.controller;

import com.simple.spring.app.dto.JwtTokenDto;
import com.simple.spring.app.dto.UserDto;
import com.simple.spring.app.dto.converter.UserConverter;
import com.simple.spring.app.entity.User;
import com.simple.spring.app.security.JwtTokenUtil;
import com.simple.spring.app.service.UserService;
import com.simple.spring.app.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        User user = UserConverter.convertToUser(userDto);
        userService.createUser(user);
        return "You have successfully registered!";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserDto userDto) {
        User user = UserConverter.convertToUser(userDto);

        userValidator.validate(user);
        String email = user.getEmail();
        String password = user.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final String token = jwtTokenUtil.generateToken(email);
        return ResponseEntity.ok(new JwtTokenDto(token));
    }
}