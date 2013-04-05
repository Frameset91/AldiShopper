/**
 * Klasse, die den Aufbau der Produkte definiert
 */
package de.aldi.shopper;

public class Product {
    
	public int productID;
	public int categoryID;
	public String name;
	public String description;
	public String unit;
	public double price;
	public int quantity;
	//public boolean selected = false;

    public Product( int productID, int categoryID, String name, String description, String unit, double price, int quantity, boolean selected) {
		this.productID = productID;
		this.categoryID = categoryID;
		this.name = name;
		this.description = description;
		this.unit = unit;
		this.price = price;
		this.quantity = quantity;
		//this.selected = selected;
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
    
    public int getQuantity(){
    	return quantity;
    }
    

//    public boolean getState() {
//	    return selected;
//    }

}
