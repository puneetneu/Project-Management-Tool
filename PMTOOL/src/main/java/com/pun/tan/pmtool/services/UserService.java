package com.pun.tan.pmtool.services;


import com.pun.tan.pmtool.domain.User;
import com.pun.tan.pmtool.exceptions.UserAlreadyExistsException;
import com.pun.tan.pmtool.repositories.UserRepository;
import com.pun.tan.pmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User saveUser(User newUser){

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }catch (Exception e){

            throw  new UserAlreadyExistsException("Username " + newUser.getUsername() + " already exist");
        }


    }


}
