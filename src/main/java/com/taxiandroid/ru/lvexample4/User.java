package com.taxiandroid.ru.lvexample4;

import java.util.ArrayList;

/**
 * Created by saperov on 21.10.15.
 */
public class User {
    public String name;
    public String hometown;
    static ArrayList<User> users;

    public User(String name, String hometown) {
        this.name = name;
        this.hometown = hometown;
    }
    public static ArrayList<User> getUsers() {
        // ArrayList<User> users = new ArrayList<User>();
        users = new ArrayList<User>();
        users.add(new User("Заказ1", "Октябрьская 24"));
        users.add(new User("Заказ2", "Военный городок 97"));
        users.add(new User("Заказ3", "Гагарина 5"));
        return users;
    }
}
