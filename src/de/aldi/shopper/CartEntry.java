/**
 * zum Speichern der Artikel + ausgew�hlte Menge
 */
package de.aldi.shopper;

import java.io.Serializable;

public class CartEntry implements Serializable {
	
	private Product product;
	private int quantity;
	
	public CartEntry(Product prod, int quant){
		product = prod;
		quantity = quant;
	}
	
	public Product getProduct(){
		return product;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quant){
		quantity = quant;
	}

}
