package com.dzytsiuk.onlinestore.entity.security;

import com.dzytsiuk.onlinestore.entity.CartItem;
import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Session {
    private final String token;
    private final User user;
    private final LocalDateTime expireDate;
    private final List<CartItem> cartItems;


    public Session(String token, User user, LocalDateTime expireDate, List<CartItem> cartItems) {
        this.token = token;
        this.user = user;
        this.expireDate = expireDate;
        this.cartItems = cartItems;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addProduct(Product product) {
        Optional<CartItem> item = cartItems.stream().filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
        if(item.isPresent()){
            CartItem cartItem = item.get();
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItems.add(new CartItem(product, 1));
        }
    }

    public void removeProduct(Product product) {
        Optional<CartItem> item = cartItems.stream().filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
        item.ifPresent(cartItem -> {
            int quantity = cartItem.getQuantity();
            if(quantity ==1){
                cartItems.remove(cartItem);
            }else{
                cartItem.setQuantity(quantity-1);
            }
        });
    }

    public static SessionBuilder builder() {
        return new SessionBuilder();
    }

    public static class SessionBuilder {

        private String token;
        private User user;
        private LocalDateTime expireDate;
        private List<CartItem> cartItems;

        public SessionBuilder withToken(String token) {
            this.token = token;
            return this;
        }


        public SessionBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public SessionBuilder withExpireDate(LocalDateTime expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        public SessionBuilder withCart(List<CartItem> cartItems){
            this.cartItems = cartItems;
            return this;
        }


        public Session build() {
            return new Session(token, user, expireDate, cartItems);
        }
    }
}