package com.example.blog.services.impl;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.UserDto;
import com.example.blog.repositories.RoleRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user=modelMapper.map(userDto,User.class);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //ROLES
        Role role=  roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User user1= userRepository.save(user);
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto user) {
        User saveUser= userRepository.save(dtoToUser(user));
        return userToDto(saveUser) ;
    }

    @Override
    public UserDto updateUser(UserDto userDto,Integer userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
         User updatedUser=userRepository.save(user);
        return userToDto(updatedUser) ;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId","id",userId));
      return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users= userRepository.findAll();
       List<UserDto> userDtos= users.stream().map((user -> userToDto(user))).collect(Collectors.toList());
        return userDtos ;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId","id",userId));
        userRepository.delete(user);
    }

    private  User dtoToUser(UserDto user){
        User user1= modelMapper.map(user,User.class);
//        User user1=new User();
//        user1.setId(user.getId());
//        user1.setName(user.getName());
//        user1.setEmail(user.getEmail());
//        user1.setPassword(user.getPassword());
//        user1.setAbout(user.getAbout());

        return user1;
    }

    private  UserDto userToDto(User user){
        UserDto user1=modelMapper.map(user,UserDto.class);
//        UserDto user1=new UserDto();
//        user1.setId(user.getId());
//        user1.setName(user.getName());
//        user1.setEmail(user.getEmail());
//        user1.setPassword(user.getPassword());
//        user1.setAbout(user.getAbout());

        return user1;
    }
}
