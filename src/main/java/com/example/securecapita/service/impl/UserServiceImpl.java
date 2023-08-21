package com.example.securecapita.service.impl;

import com.example.securecapita.dto.UserDTO;
import com.example.securecapita.dto.UserMapperDTO;
import com.example.securecapita.model.User;
import com.example.securecapita.repository.UserRepository;
import com.example.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user)
    {
        return UserMapperDTO.fromUser(userRepository.create(user));
    }
}
