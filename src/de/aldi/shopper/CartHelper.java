/**
 * speichert die ausgewählten Artikel, die in "Proceed" angezeigt werden sollen
 */
package de.aldi.shopper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CartHelper {
	
    private static Map<Product, CartEntry> cartMap = new HashMap<Product, CartEntry>();
    
    
	public static void setQuantity(Product product, int quantity){
		CartEntry curEntry = cartMap.get(product);
		if (quantity <= 0){			// wenn Anzahl weniger oder gleich 0, Produkt entfernen
			removeProduct(product);
			return;
		}
		
		if (curEntry == null){		// wenn Eintrag nicht existiert, neuen erstellen
			curEntry = new CartEntry(product, quantity);
			cartMap.put(product, curEntry);
			return;
		}
		
		// Anzahl updaten
		curEntry.setQuantity(quantity);
	}
	
	public static int getProductQuantity(Product product){
		// aktueller Eintrag
		CartEntry curEntry = cartMap.get(product);
		
		if (curEntry != null)
			return curEntry.getQuantity();
		
		return 0;
	}
	
	public static void removeProduct(Product product){
		cartMap.remove(product);
	}
	
	public static List<Product> getCartList(){
		List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
		for (Product p : cartMap.keySet()){
			cartList.add(p);
		}
		return cartList;
	}
	
	
	public static void removeAll(){
		cartMap.clear();
	}

}
