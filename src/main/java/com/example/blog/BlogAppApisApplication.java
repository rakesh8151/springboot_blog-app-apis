package com.example.blog;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApisApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

    @Override
    public  void  run(String... args) throws Exception{
       try {
           Role role=new Role();
           role.setId(AppConstants.NORMAL_USER);
           role.setName("ROLE_USER");
           Role role1=new Role();
           role1.setId(AppConstants.ADMIN_USER);
           role1.setName("ROLE_ADMIN");
           List<Role> roles=List.of(role1,role);
           roleRepository.saveAll(roles);
       }catch (Exception e){
           e.printStackTrace();

       }
    }
}
