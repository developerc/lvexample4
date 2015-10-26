package com.taxiandroid.ru.lvexample4;

import java.util.ArrayList;

/**
 * Created by saperov on 21.10.15.
 */
public class User {
    public String name;
    public String hometown;
    public String tvIndivid;
    static ArrayList<User> users;

    public User(String name, String hometown, String tvIndivid) {
        this.name = name;
        this.hometown = hometown;
        this.tvIndivid = tvIndivid;
    }
    public static ArrayList<User> getUsers() {
        // ArrayList<User> users = new ArrayList<User>();
        users = new ArrayList<User>();
        users.add(new User("Парковый Гагарина 5а/1", "срочный", "общий"));
        users.add(new User("Алексеевские планы Ореховая 15 возле шлагбаума", "срочный", "общий"));
        users.add(new User("Фастовецкая Азина 26", "срочный", "индивидуальный"));
        return users;
    }
}
