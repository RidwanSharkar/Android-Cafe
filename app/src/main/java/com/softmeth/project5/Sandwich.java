package com.softmeth.project5;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sandwich class to extend MenuItem abstract class
 * Represents a Sandwich item in the menu.
 * It allows specifying the combination of Meat, Bread, and Addons to calculate total price
 *
 * @author Ridwan Sharkar
 */
public class Sandwich extends MenuItem
{
    /**
     * Enumeration for available meat choices in a sandwich.
     */
    private enum MeatChoice  { CHICKEN, FISH, BEEF }
    /**
     * Enumeration for available bread choices in a sandwich.
     */
    private enum BreadChoice { WHEAT_TOAST, BAGEL, SOUR_DOUGH }
    /**
     * Enumeration for available add-ons in a sandwich.
     */
    private enum AddOns      { CHEESE, LETTUCE, TOMATOES, ONIONS }
    private final MeatChoice meatChoice;
    private final BreadChoice breadChoice;
    private final List<AddOns> addOnsList;
    private static final double CHICKEN_PRICE = 8.99;
    private static final double FISH_PRICE = 9.99;
    private static final double BEEF_PRICE = 10.99;
    private static final double CHEESE_PRICE = 1.00;
    private static final double LETTUCE_PRICE = 0.30;
    private static final double TOMATOES_PRICE = 0.30;
    private static final double ONIONS_PRICE = 0.30;
    /*============================================================================================*/

    /**
     * Constructs a Sandwich object with specified meat, bread, and a list of add-on strings.
     * Converts meat and bread choices from strings to their respective enums.
     *
     * @param meatChoice    the choice of meat for the sandwich, given as a string
     * @param breadChoice   the choice of bread for the sandwich, given as a string
     * @param addOnsStrings a list of strings representing add-on choices
     */
    public Sandwich(String meatChoice, String breadChoice, List<String> addOnsStrings)
    {
        this.meatChoice = meatChoice != null ? MeatChoice.valueOf(meatChoice.toUpperCase()) : null;
        this.breadChoice = breadChoice != null ? BreadChoice.valueOf(breadChoice.toUpperCase()) : null;
        this.addOnsList = new ArrayList<>();
        if (addOnsStrings != null) {
            for (String addOnString : addOnsStrings) {
                this.addOnsList.add(AddOns.valueOf(addOnString.toUpperCase()));
            }
        }
    }

    /**
     * Calculates and returns the total price of the sandwich based on its meat and add-on selections.
     * Overwrites Abstract Method in MenuItem
     *
     * @return the total price of the sandwich
     */
    @Override
    public double price()
    {
        double totalPrice = 0.0;
        if (meatChoice == null) {
            return 0.00;
        }
        switch (meatChoice)
        {
            case CHICKEN:
                totalPrice += CHICKEN_PRICE;
                break;
            case FISH:
                totalPrice += FISH_PRICE;
                break;
            case BEEF:
                totalPrice += BEEF_PRICE;
                break;
            default:
                throw new IllegalStateException("Invalid Meat Selection");
        }
        if (addOnsList != null)
        {
            for (AddOns addOn : addOnsList)
            {
                switch (addOn)
                {
                    case CHEESE:
                        totalPrice += CHEESE_PRICE;
                        break;
                    case LETTUCE:
                        totalPrice += LETTUCE_PRICE;
                        break;
                    case TOMATOES:
                        totalPrice += TOMATOES_PRICE;
                        break;
                    case ONIONS:
                        totalPrice += ONIONS_PRICE;
                        break;
                    default:
                        throw new IllegalStateException("Invalid Add-on Selection");
                }
            }
        }
        return totalPrice;
    }

    /**
     * Provides a string representation of the sandwich including its meat, bread, and list of add-ons.
     *
     * @return a string description of the sandwich
     */
    @NonNull
    @Override
    public String toString() {
        return String.format("Sandwich: Meat - %s, Bread - %s, Add-ons - %s",
                this.meatChoice.name(), this.breadChoice.name(), this.addOnsList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    /**
     * Factory method to create a new Sandwich instance based on input strings for meat, bread, and add-ons.
     * This method converts the bread label to its corresponding enum using a helper method and constructs the sandwich.
     *
     * @param meatChoice a string representing the choice of meat
     * @param breadLabel a string label for the type of bread
     * @param addOns a list of strings representing additional toppings or condiments
     * @return a new instance of Sandwich configured with specified options
     */
    public static Sandwich createSandwich(String meatChoice, String breadLabel, List<String> addOns) {
        BreadChoice breadChoice = getBreadChoiceFromString(breadLabel);
        return new Sandwich(meatChoice, String.valueOf(breadChoice), addOns);
    }

    /**
     * Converts a string label describing the bread type into the corresponding BreadChoice enum.
     * This method supports translation from human-readable form to enum values used in the system.
     *
     * @param breadLabel the string description of the bread
     * @return the corresponding BreadChoice enum value
     * @throws IllegalArgumentException if the breadLabel does not correspond to any known bread type
     */
    private static BreadChoice getBreadChoiceFromString(String breadLabel) {
        switch (breadLabel) {
            case "Wheat Toast":
                return BreadChoice.WHEAT_TOAST;
            case "Sour Dough":
                return BreadChoice.SOUR_DOUGH;
            case "Bagel":
                return BreadChoice.BAGEL;
            default:
                throw new IllegalArgumentException("Unknown Bread: " + breadLabel);
        }
    }
}