package com.educative.ecommerce.controllers;

import com.educative.ecommerce.common.ApiResponse;
import com.educative.ecommerce.dto.ProductDto;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.model.WishList;
import com.educative.ecommerce.service.AuthenticationService;
import com.educative.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishlistService;

    @Autowired
     AuthenticationService authenticationService;

    // Two Apis

    // save product in wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token")String token){
        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        // save the item in wishlist

        WishList wishList = new WishList(user, product);

        if (wishlistService.createWishlist(wishList, user) == 0) {
            ApiResponse apiResponse = new ApiResponse(false,"Item already added to wishlist");

            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
           else {
            ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");

            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        


    }

    // get all wishlist items for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        List<ProductDto> wishListForUser = wishlistService.getWishListForUser(user);

        return new ResponseEntity<>(wishListForUser, HttpStatus.OK);

    }

   @DeleteMapping("/deleteWishlist")
    public ResponseEntity<ApiResponse> deleteWishList(@RequestParam("token")String token ) {

        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        wishlistService.deleteWishlist(user);

        return new ResponseEntity<>(new ApiResponse(true, "Deleted WishList"), HttpStatus.OK);

    }

    @DeleteMapping("/deletewishlistitem")
    public ResponseEntity<ApiResponse> deleteWishListItem(@RequestBody Product product, @RequestParam("token")String token ) {

        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        wishlistService.deleteWishlistItem(user, product);

        return new ResponseEntity<>(new ApiResponse(true, "Deleted WishList Item"), HttpStatus.OK);

    }





}
