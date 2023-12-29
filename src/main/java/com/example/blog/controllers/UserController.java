package com.example.blog.controllers;

import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.UserDto;
import com.example.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity< UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok( userService.createUser(userDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity< UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){

        return ResponseEntity.ok( userService.updateUser(userDto,userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity< UserDto> getUserById( @PathVariable Integer userId){

        return ResponseEntity.ok( userService.getUserById(userId));
    }

    @GetMapping()
    public ResponseEntity<List <UserDto>> getAllUser(){

        return ResponseEntity.ok( userService.getAllUsers());
    }


    @DeleteMapping ("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
         userService.deleteUser(userId);
         return new  ResponseEntity(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }

}
