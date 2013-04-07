/**
 * Klasse, die den Aufbau der Produkte definiert
 */
package de.aldi.shopper;

import java.io.Serializable;

import android.widget.NumberPicker;

public class Product implements Serializable{
    
	public int productID;
	public int categoryID;
	public String name;
	public String description;
	public String unit;
	public double price;
	//public int quantity;
	public NumberPicker quantityPicker;
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
    
    public NumberPicker getQuantPicker(){
    	return quantityPicker;
    }
//    public int getQuantity(){
//    	Integer q = quantityPicker.getValue();
//    	if(q == null)
//    		quantityPicker.setValue(0);
//    	return quantityPicker.getValue();
//    }
    

//    public boolean getState() {
//	    return selected;
//    }

}
