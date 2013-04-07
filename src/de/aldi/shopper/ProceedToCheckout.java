package de.aldi.shopper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		if (cartAdapter != null) {
			cartAdapter.notifyDataSetChanged();
		}

		double subTotal = 0;
		for (Product p : cartList) {
			int quantity = CartHelper.getProductQuantity(p);
			subTotal += p.price * quantity;
		}

		TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
		DecimalFormat f = new DecimalFormat("#0.00");
		productPriceTextView.setText("Summe: " + f.format(subTotal) + "€");
	}

	public void onCheckout(View view) {
		Intent checkout = new Intent(this, Checkout.class);
		startActivity(checkout);
	}

	public void onSaveCart(View view) {
		String fileName = "activeCart";
		FileOutputStream fileOutput = null;
		ObjectOutputStream objectOutput = null;

		try {
			fileOutput = this.openFileOutput(fileName, MODE_PRIVATE);
			objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(cartList);
			Toast.makeText(this, "Einkaufswagen gespeichert", Toast.LENGTH_SHORT)
					.show();
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Datei nicht gefunden", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proceed_to_checkout, menu);
		return true;
	}

}
