package com.educative.ecommerce.controllers;

import com.educative.ecommerce.dto.product.ResponseDto;
import com.educative.ecommerce.dto.product.User.SignInDto;
import com.educative.ecommerce.dto.product.User.SignInResponseDto;
import com.educative.ecommerce.dto.product.User.SignupDto;
import com.educative.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    // two apis

    //signup

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

    //signin

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }
}
