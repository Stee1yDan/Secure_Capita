package com.example.securecapita.controller;

import com.example.securecapita.dto.UserDTO;
import com.example.securecapita.model.HttpResponse;
import com.example.securecapita.model.User;
import com.example.securecapita.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user)
    {
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.created(getUri()).body
                (
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("user", userDTO))
                                .message("User created")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

    private URI getUri()
    {
        return URI.create
                (
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/user/get/<userId>")
                                .toUriString()
                );
    }
}
