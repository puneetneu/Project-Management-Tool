package com.pun.tan.pmtool.web;

import com.pun.tan.pmtool.domain.User;
import com.pun.tan.pmtool.services.UserService;
import com.pun.tan.pmtool.services.ValidationError;
import com.pun.tan.pmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ValidationError validationError;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){

        userValidator.validate(user, bindingResult);
        ResponseEntity<?> errorMap = validationError.ValidationError(bindingResult);
        if(errorMap!=null) return errorMap;

        User newuser = userService.saveUser(user);

        return new ResponseEntity<User>(newuser, HttpStatus.CREATED);
    }
}
