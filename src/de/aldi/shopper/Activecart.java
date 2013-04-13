package de.aldi.shopper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activecart extends Activity {

	private List<Product> loadedCartList;
	private Map<Product, Integer> loadedCartMap;
	private CartAdapter cartAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activecart);
		
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		try {
			fileIn = this.openFileInput("activeCart");
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Datei nicht gefunden", Toast.LENGTH_SHORT).show();
		}
		try {
			objectIn = new ObjectInputStream(fileIn);
			loadedCartMap = (Map<Product, Integer>) objectIn.readObject();
			loadedCartList = new Vector<Product>(loadedCartMap.keySet().size());
			for (Product p : loadedCartMap.keySet()){
				int quantity = loadedCartMap.get(p);
				CartHelper.setQuantity(p, quantity);
				loadedCartList.add(p);
			}
		} catch (StreamCorruptedException e) {
			Toast.makeText(this, "Stream Corruption", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
		} catch (ClassNotFoundException e) {
			Toast.makeText(this, "Class not Found", Toast.LENGTH_SHORT).show();
		}
		
		ListView cart = (ListView) findViewById(R.id.cartList);
		cartAdapter = new CartAdapter(loadedCartList, getLayoutInflater());
		cart.setAdapter(cartAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Daten aktualisieren
		if (cartAdapter != null) {
			cartAdapter.notifyDataSetChanged();
		}

		double subTotal = 0;
		for (Product p : loadedCartList) {
			int quantity = CartHelper.getProductQuantity(p);
			subTotal += p.price * quantity;
		}

		TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
		DecimalFormat f = new DecimalFormat("#0.00");
		productPriceTextView.setText(f.format(subTotal) +" €");
	}

	
	public void onCheckout(View view) {
		TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
		String total = productPriceTextView.getText().toString();
		Intent checkout = new Intent(this, Checkout.class);
		checkout.putExtra("total", total);
		startActivity(checkout);
	}
	
	public void onEditCart(View view){
		Intent newcart = new Intent(this, Newcart.class);
		startActivity(newcart);
	}

}
