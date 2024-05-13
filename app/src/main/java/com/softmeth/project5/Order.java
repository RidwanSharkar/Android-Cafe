package com.softmeth.project5;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages orders in the RU Café system. Each order is unique and contains a list of menu items.
 * This class uses a singleton pattern to ensure only one instance of the order exists at a time.
 * The order contains items of type MenuItem, which can be donuts, coffee, or sandwiches.
 *
 * @author Ridwan Sharkar
 */
public class Order
{
    private static int nextOrderNumber = 1;
    private final int orderNumber;
    private final List<MenuItem> items;
    private static Order instance;

    /**
     * Returns the singleton instance of the Order.
     * If the instance does not exist, it creates a new one.
     *
     * @return the singleton instance of the Order
     */
    public static Order getInstance()
    {
        if (instance == null)
        {
            instance = new Order();
        }
        return instance;
    }

    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes the order with a unique number and an empty list of menu items.
     */
    private Order()
    {
        this.orderNumber = nextOrderNumber++;
        this.items = new ArrayList<>();
    }

    /**
     * Adds a menu item to the order.
     *
     * @param item The menu item to be added.
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Removes a menu item from the order.
     *
     * @param item The menu item to be removed.
     */
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    /**
     * Calculates the total price of all items in the order.
     *
     * @return Total price of the order.
     */
    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.price();
        }
        return total;
    }

    /**
     * Gets the list of items in the order.
     *
     * @return The list of menu items.
     */
    public List<MenuItem> getItems() {
        return new ArrayList<>(items); // Return a copy to preserve encapsulation
    }

    /**
     * Resets the order by creating a new singleton instance.
     * This method can be used when starting a new order, ensuring the previous order's data is cleared.
     */
    public void resetOrder() {
        instance = new Order();
    }
}