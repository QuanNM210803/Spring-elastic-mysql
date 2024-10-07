package com.example.springelasticmysql.controller;

import com.example.springelasticmysql.dto.UserDTO;
import com.example.springelasticmysql.service.IUserService;
import com.example.springelasticmysql.utils.PathResources;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping(PathResources.USER)
public class UserController {
    private final IUserService userService;

    @PostMapping(PathResources.SAVE)
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userDTO));
    }

    @GetMapping(PathResources.FINDBYID + "/{" + PathResources.ID + "}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(PathResources.FIND_ALL)
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(PathResources.UPDATE_USER)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
