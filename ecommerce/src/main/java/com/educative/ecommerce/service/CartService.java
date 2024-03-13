package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.cart.AddToCartDto;
import com.educative.ecommerce.dto.cart.CartDto;
import com.educative.ecommerce.dto.cart.CartItemDto;
import com.educative.ecommerce.exceptions.CustomException;
import com.educative.ecommerce.model.Cart;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;
    public void addToCart(AddToCartDto addToCartDto, User user) {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart(product,user, addToCartDto.getQuantity());



        // save the cart

        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user) {
        final List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart : cartList) {

            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity()*cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto(cartItems, totalCost);

        return cartDto;

    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if(optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("cart item does not belong to user: " + cartItemId);
        }

        cartRepository.delete(cart);

    }
}
