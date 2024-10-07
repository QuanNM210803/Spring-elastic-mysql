package com.example.springelasticmysql.service;

import com.example.springelasticmysql.dto.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO save(UserDTO userDTO);
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO updateUser(UserDTO userDTO);
}
