package com.softmeth.project5;
import androidx.annotation.NonNull;

/**
 * Coffee class to extend MenuItem abstract class
 * Represents a Coffee item in the menu
 * It allows specifying cup size and number of add-ons to calculate total price
 *
 * @author Ridwan Sharkar
 */
public class Coffee extends MenuItem
{
    /**
     * Enum for coffee cup sizes with predefined size options.
     */
    private enum CupSize { SHORT, TALL, GRANDE, VENTI }
    private final CupSize cupSize;
    private final int addonCount;
    private static final double BASE_PRICE = 1.99;
    private static final double SIZE_INCREMENT_PRICE = 0.50;
    public static final double ADDON_PRICE = 0.30;
    /*============================================================================================*/

    /**
     * Constructs a new Coffee instance with specified cup size and add-ons count.
     *
     * @param cupSize    the coffee cup size as a string
     * @param addonCount the number of add-ons added to the coffee
     */
    public Coffee(String cupSize, int addonCount)
    {
        this.cupSize = CupSize.valueOf(cupSize.toUpperCase());
        this.addonCount = addonCount;
    }

    /**
     * Calculates the total price of the coffee based on base price, size, and number of add-ons.
     * Overwrites Abstract Method in MenuItem
     *
     * @return the total price of the coffee
     */
    public double price()
    {
        double price = BASE_PRICE;
        price += this.cupSize.ordinal() * SIZE_INCREMENT_PRICE;
        price += this.addonCount * ADDON_PRICE;
        return price;
    }

    /**
     * Returns a string representation of the coffee order, including cup size and number of added flavors.
     *
     * @return a formatted string detailing the coffee size and add-ons
     */
    @NonNull
    @Override
    public String toString()
    {
        return String.format("Coffee: Size-%s, Added Flavors-%s", this.cupSize.name(), this.addonCount);
    }
}