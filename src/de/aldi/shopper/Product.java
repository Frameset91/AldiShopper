/**
 * Klasse, die den Aufbau der Produkte definiert
 */
package de.aldi.shopper;

import java.io.Serializable;

public class Product implements Serializable{
    
	public int productID;
	public int categoryID;
	public String name;
	public String description;
	public String unit;
	public double price;

    public Product( int productID, int categoryID, String name, String description, String unit, double price) {
		this.productID = productID;
		this.categoryID = categoryID;
		this.name = name;
		this.description = description;
		this.unit = unit;
		this.price = price;
    }
    
    public int getID(){
    	return productID;
    }

    public String getName() {
	    return name;
    }

    public String getUnit() {
	    return unit;
    }
    
    public double getPrice(){
    	return price;
    }
    
    public String getDescription(){
    	return description;
    }

}
