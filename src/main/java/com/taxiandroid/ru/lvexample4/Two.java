package com.taxiandroid.ru.lvexample4;

import java.util.ArrayList;

/**
 * Created by saperov on 25.10.15.
 */
public class Two {
    public String two_item;

    static ArrayList<Two> two_items;

    public Two(String two_item) {
        this.two_item = two_item;

    }
    public static ArrayList<Two> getTwoItem() {
        // ArrayList<User> users = new ArrayList<User>();
        two_items = new ArrayList<Two>();
        two_items.add(new Two("Назначение поездки"));
        two_items.add(new Two("SMS"));
        two_items.add(new Two("CALL"));
        two_items.add(new Two("Таксометр"));
        return two_items;
    }
}
