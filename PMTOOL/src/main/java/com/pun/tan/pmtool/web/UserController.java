package com.pun.tan.pmtool.web;

import com.pun.tan.pmtool.domain.User;
import com.pun.tan.pmtool.payload.JWTLoginSuccesResponse;
import com.pun.tan.pmtool.payload.LoginRequest;
import com.pun.tan.pmtool.security.JWTTokenProvider;
import com.pun.tan.pmtool.services.UserService;
import com.pun.tan.pmtool.services.ValidationError;
import com.pun.tan.pmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Security;

import static com.pun.tan.pmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ValidationError validationError;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;




    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){

        userValidator.validate(user, bindingResult);
        ResponseEntity<?> errorMap = validationError.ValidationError(bindingResult);
        if(errorMap!=null) return errorMap;

        User newuser = userService.saveUser(user);

        return new ResponseEntity<User>(newuser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<?> errorMap = validationError.ValidationError(bindingResult);
        if(errorMap!=null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPasswrod()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToke(authentication);

        return ResponseEntity.ok(new JWTLoginSuccesResponse(true, jwt));
    }
}
