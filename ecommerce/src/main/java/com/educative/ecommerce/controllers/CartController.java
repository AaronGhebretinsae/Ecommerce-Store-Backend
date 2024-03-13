package com.educative.ecommerce.controllers;

import com.educative.ecommerce.common.ApiResponse;
import com.educative.ecommerce.dto.cart.AddToCartDto;
import com.educative.ecommerce.dto.cart.CartDto;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.service.AuthenticationService;
import com.educative.ecommerce.service.CartService;
import com.educative.ecommerce.service.ProductService;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;


    //post cart api

    @PostMapping("/addtocart")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){
        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto,user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    // get all cart items for a user
    //To Do: use @PreAuthorize Annotation to make app more robust instead of copying and pasting authentication functions

    @GetMapping("/")
    public ResponseEntity<CartDto> getAllCartItems(@RequestParam("token") String token) {

        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        //get cart items

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // delete a cart item for a user

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token){
        // authenticate the token

        authenticationService.authenticate(token);

        // find the user

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

    }

    // TO DO: Implement Update cart API


}
