package com.softmeth.project5;

/**
 * Represents an item in the donut menu.
 * This class provides storage for information about a specific donut, including its name and price.
 *
 * @author Ridwan Sharkar
 */
public class DonutItem {
	private String name;
	private String price;

	/**
	 * Constructs a new DonutItem with specified name and price.
	 *
	 * @param name the name of the donut.
	 * @param price the price of the donut as a formatted string (e.g., "$1.99").
	 */
	public DonutItem(String name, String price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * Retrieves the name of the donut.
	 *
	 * @return the name of the donut.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the price of the donut.
	 *
	 * @return the price of the donut as a string.
	 */
	public String getPrice() {
		return price;
	}
}