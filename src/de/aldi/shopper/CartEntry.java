/**
 * zum Speichern der Artikel + ausgewählte Menge
 */
package de.aldi.shopper;

public class CartEntry {
	
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
