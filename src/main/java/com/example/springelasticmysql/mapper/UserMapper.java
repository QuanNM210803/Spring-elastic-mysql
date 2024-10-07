package com.example.springelasticmysql.mapper;

import com.example.springelasticmysql.dto.UserDTO;
import com.example.springelasticmysql.model.User;
import com.example.springelasticmysql.model.UserModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User toUser(UserDTO userDTO);

    List<User> toUsers(List<UserDTO> userDTOS);

    UserModel toUserModel(User user);
}
