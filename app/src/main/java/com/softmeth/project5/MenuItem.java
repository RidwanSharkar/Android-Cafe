package com.softmeth.project5;

/**
 * Abstract class representing a general item on the menu in RU Café.
 * This class is to be extended by specific menu item classes such as Coffee, Donut, and Sandwich.
 *
 * @author Ridwan Sharkar
 */

public abstract class MenuItem
{
    /**
     * Calculates and returns the price of the menu item.
     * This method must be implemented by each subclass.
     *
     * @return the price of the menu item
     */
    public abstract double price();
}



