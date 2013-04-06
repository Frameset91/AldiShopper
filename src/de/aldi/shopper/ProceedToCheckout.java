package de.aldi.shopper;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class ProceedToCheckout extends Activity {
	
	private List<Product> cartList;
	private CartAdapter cartAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proceed_to_checkout);
		
		cartList = CartHelper.getCartList();
		
		ListView listViewCatalog = (ListView) findViewById(R.id.cartList);
		cartAdapter = new CartAdapter(cartList, getLayoutInflater());
		listViewCatalog.setAdapter(cartAdapter);
	}
	
	 
	 @Override
	 protected void onResume() {
	  super.onResume();
	  
	  // Daten aktualisieren
	  if(cartAdapter != null) {
	   cartAdapter.notifyDataSetChanged();
	  }
	  
	  double subTotal = 0;
	  for(Product p : cartList) {
	   int quantity = CartHelper.getProductQuantity(p);
	   subTotal += p.price * quantity;
	  }
	  
	  TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
	  DecimalFormat f = new DecimalFormat("#0.00");
	  productPriceTextView.setText("Summe: " + f.format(subTotal) + "€");
	 }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proceed_to_checkout, menu);
		return true;
	}

}
