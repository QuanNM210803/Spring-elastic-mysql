package com.example.springelasticmysql.service;

import com.example.springelasticmysql.dto.UserDTO;
import com.example.springelasticmysql.mapper.UserMapper;
import com.example.springelasticmysql.model.User;
import com.example.springelasticmysql.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user=userMapper.toUser(userDTO);
        User userSave=userRepository.save(user);
        return userMapper.toUserDTO(userSave);
    }

    @Override
    public UserDTO findById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("NOT FOUND USER"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users=userRepository.findAll();
        return userMapper.toUserDTOs(users);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user=userRepository.findById(userDTO.getId()).orElseThrow(()-> new FindException("NOT FOUND USER"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        return userMapper.toUserDTO(userRepository.save(user));
    }
}
