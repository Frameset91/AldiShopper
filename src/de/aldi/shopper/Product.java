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
	//public boolean selected = false;

    public Product( int productID, int categoryID, String name, String description, String unit, double price, boolean selected) {
		this.productID = productID;
		this.categoryID = categoryID;
		this.name = name;
		this.description = description;
		this.unit = unit;
		this.price = price;
		//this.selected = selected;
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

//    public boolean getState() {
//	    return selected;
//    }

}
