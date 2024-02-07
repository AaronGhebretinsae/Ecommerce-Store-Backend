package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.product.ResponseDto;
import com.educative.ecommerce.dto.product.User.SignInDto;
import com.educative.ecommerce.dto.product.User.SignInResponseDto;
import com.educative.ecommerce.dto.product.User.SignupDto;
import com.educative.ecommerce.exceptions.AuthenticationFailException;
import com.educative.ecommerce.exceptions.CustomException;
import com.educative.ecommerce.model.AuthenticationToken;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.repository.UserRepository;
import jakarta.xml.bind.DatatypeConverter;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;
    public ResponseDto signup(SignupDto signupDto) {

        // check if user is already present

        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {

            // we have a User
            throw new CustomException("user already present");
        }

        //hash the password
        String encryptedpassword = signupDto.getPassword();

        try {

            encryptedpassword = hashPassword(signupDto.getPassword());

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            throw new CustomException(e.getMessage());

        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedpassword);

        userRepository.save(user);

        //save the user

        //create the token

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto( "success!" ,  "response");

        return responseDto;

    }

    private String hashPassword(String password) throws NoSuchAlgorithmException{

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;

    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //find user by email

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        //hash the password
        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {

                throw new AuthenticationFailException("wrong password");

            }
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        };
        //compare the password in DB

        //if password match

        AuthenticationToken token = authenticationService.getToken(user);

        //retrieve the token

        if (Objects.isNull(token)){

            throw new CustomException("token is not present");

        }

        return new SignInResponseDto("success", token.getToken());

        //return response
    }

}
