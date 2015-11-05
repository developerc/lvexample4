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
        /*users.add(new User("Парковый Гагарина 5а/1", "срочный", "общий"));
        users.add(new User("Алексеевские планы Ореховая 15 возле шлагбаума", "срочный", "общий"));
        users.add(new User("Фастовецкая Азина 26", "срочный", "индивидуальный"));*/
        //users.add(new User(MainActivity.adres.get(0), "срочный", "общий"));
        users.add(new User("Нет заказов","срочный","общий"));
        return users;
    }
    public  static ArrayList<User> UpdateUsers() {
        users = new ArrayList<User>();
        int cnt;
        cnt = MainActivity.adres.size();
        for (int i=0; i<cnt; i++) {
            users.add(new User(MainActivity.adres.get(i), "срочный", "общий"));
        }
        return users;
    }
}
