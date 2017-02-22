package com.taotao.cart;


import com.taotao.cart.pojo.User;

/**
 * Created by shenchao on 2017/2/19.
 */
public class UserThreadLocal {
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();
    private UserThreadLocal() {

    }
    public static void set(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }
}
