package de.aldi.shopper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class Activecart extends Activity {

	private List<Product> cartList;
	private CartAdapter cartAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proceed_to_checkout);
		
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		try {
			fileIn = this.openFileInput("activeCart");
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Datei nicht gefunden", Toast.LENGTH_SHORT).show();
		}
		try {
			objectIn = new ObjectInputStream(fileIn);
			cartList = (List<Product>) objectIn.readObject();
		} catch (StreamCorruptedException e) {
			Toast.makeText(this, "Stream Corruption", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
		} catch (ClassNotFoundException e) {
			Toast.makeText(this, "Class not Found", Toast.LENGTH_SHORT);
		}
		
		ListView cart = (ListView) findViewById(R.id.cartList);
		cartAdapter = new CartAdapter(cartList, getLayoutInflater());
		cart.setAdapter(cartAdapter);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activecart, menu);
		return true;
	}

}
