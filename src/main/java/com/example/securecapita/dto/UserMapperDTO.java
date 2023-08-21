package com.example.securecapita.dto;

import com.example.securecapita.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapperDTO
{
    public static UserDTO fromUser(User user)
    {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User toUser(UserDTO userDTO)
    {
        User user = new User();
        BeanUtils.copyProperties(user, userDTO);
        return user;
    }
}
