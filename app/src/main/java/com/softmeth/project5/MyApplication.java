package com.softmeth.project5;
import android.app.Application;

import java.util.ArrayList;

/**
 * Extends the Android Application class to store all the orders made during the app's lifecycle.
 *
 * @author Ridwan Sharkar
 */
public class MyApplication extends Application {

    private ArrayList<ArrayList<MenuItem>> allOrders = new ArrayList<>();

    /**
     * Retrieves a list of all orders stored in the application.
     * Each order is represented as an ArrayList of MenuItem objects, which contain details about each ordered item.
     *
     * @return the list containing all the orders.
     */
    public ArrayList<ArrayList<MenuItem>> getAllOrders() {
        return allOrders;
    }
}