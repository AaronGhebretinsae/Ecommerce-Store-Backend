package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.ProductDto;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.model.WishList;
import com.educative.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public Integer createWishlist(WishList wL, User user) {

        List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);



        for (WishList wishList:wishLists) {

            if(wishList.getProduct().getId() == wL.getProduct().getId()) { return 0;}

        }

        wishListRepository.save(wL);

        return 1;

    }

    public  List<ProductDto> getWishListForUser(User user) {

        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<ProductDto> productDtos = new ArrayList<>();

        for (WishList wishList:wishLists) {

            productDtos.add(productService.getProductDto(wishList.getProduct()));

        }

        return productDtos;
    }

    public void deleteWishlist(User user){

         List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);

        for (WishList wishList:wishLists) {

            wishListRepository.delete(wishList);

        }

    }

    public void deleteWishlistItem(User user, Product product){

        List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);



        for (WishList wishList:wishLists) {

            if(wishList.getProduct().getId() == product.getId()) { wishListRepository.delete(wishList);}

        }

    }
}
