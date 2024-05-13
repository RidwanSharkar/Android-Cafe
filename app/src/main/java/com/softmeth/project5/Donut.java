package com.softmeth.project5;
import androidx.annotation.NonNull;
import java.util.Objects;

/**
 * Donut class to extend MenuItem abstract class
 * Represents a Donut item in the menu.
 * It allows specifying donut flavor and type, used to calculate total price.
 *
 * @author Ridwan Sharkar
 */
public class Donut extends MenuItem
{
    /**
     * Enum for types of donuts available in the menu.
     */
    private enum DonutType { YEAST, CAKE, DONUT_HOLE }
    private static final double YEAST_DONUT_PRICE = 1.79;
    private static final double CAKE_DONUT_PRICE = 1.89;
    private static final double DONUT_HOLE_PRICE = 0.39;
    private final DonutType donutType;
    private final String donutFlavor;
    /*============================================================================================*/

    /**
     * Constructs a new Donut instance with specified type and flavor.
     *
     * @param donutType  the type of the donut as a string
     * @param donutFlavor the flavor of the donut
     */
    public Donut(String donutType, String donutFlavor)
    {
        this.donutType = DonutType.valueOf(donutType);
        this.donutFlavor = donutFlavor;
    }

    /**
     * Calculates and returns the price of the donut based on only its type.
     * Overwrites Abstract Method in MenuItem
     *
     * @return the calculated price of the donut
     */
    @Override
    public double price()
    {
        switch (donutType)
        {
            case YEAST:
                return YEAST_DONUT_PRICE;
            case CAKE:
                return CAKE_DONUT_PRICE;
            case DONUT_HOLE:
                return DONUT_HOLE_PRICE;
            default:
                throw new IllegalStateException("Unknown Donut Type: " + donutType);
        }
    }

    /**
     * Returns a string representation of the donut, including its flavor.
     *
     * @return a string describing the donut's flavor
     */
    @NonNull
    @Override
    public String toString()
    {
        return String.format("Donut: %s", this.donutFlavor);
    }

    /**
     * Compares this Donut with another object for equality (same type and flavor).
     *
     * @param obj the object to compare with
     * @return true if the given object represents a Donut equivalent to this donut, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Donut donut = (Donut) obj;
        return donutType == donut.donutType &&
                (Objects.equals(donutFlavor, donut.donutFlavor));
    }

    /**
     * Returns a hash code value for the donut.
     *
     * @return a hash code value for this donut
     */
    @Override
    public int hashCode() {
        int result = donutType != null ? donutType.hashCode() : 0;
        result = 31 * result + (donutFlavor != null ? donutFlavor.hashCode() : 0);
        return result;
    }
}