/**
 * Activity zeigt Übersicht über die ausgewählten Artikel.
 * Der Kunde kann den Einkaufswagen speichern oder fortfahren und die Bestellung absenden
 */

package de.aldi.shopper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

		// Gesamtsumme berechnen
		double subTotal = 0;
		for (Product p : cartList) {
			int quantity = CartHelper.getProductQuantity(p);
			subTotal += p.price * quantity;
		}
		TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
		DecimalFormat f = new DecimalFormat("#0.00");
		productPriceTextView.setText(f.format(subTotal) +" €");
	}
/**
 * Fortfahren und Bestellung absenden
 */
	public void onCheckout(View view) {
		TextView productPriceTextView = (TextView) findViewById(R.id.Subtotal);
		String total = productPriceTextView.getText().toString();
		Intent checkout = new Intent(this, Checkout.class);
		// Der nachfolgenden Activity die Gesamtsumme übergeben, damit diese nicht erneut berechnet werden muss
		checkout.putExtra("total", total);
		startActivity(checkout);
	}

	/**
	 * Abspeichern des Einkaufswagens für ein späteres Bestellen -> ActiveCart
	 */
	public void onSaveCart(View view) {
		if (!cartList.isEmpty()) {
			// Aufrufen der Datei, in der der Wagen gespeichert wird
			String fileName = "activeCart";
			FileOutputStream fileOutput = null;
			ObjectOutputStream objectOutput = null;
			// neue Map, die die Produkte und die dazugehörigen Mengen speichert
			Map<Product, Integer> cartQuantity = new HashMap<Product, Integer>();
			for (Product p : cartList) {
				int quantity = CartHelper.getProductQuantity(p);
				cartQuantity.put(p, quantity);
			}
			// Schreiben in die Datei mit OutputStream
			try {
				fileOutput = this.openFileOutput(fileName, MODE_PRIVATE);
				objectOutput = new ObjectOutputStream(fileOutput);
				objectOutput.writeObject(cartQuantity);
				Toast.makeText(this, "Einkaufswagen gespeichert",
						Toast.LENGTH_SHORT).show();
				System.out.println("Warenkorb wurde gespeichert");
			} catch (FileNotFoundException e) {
				Toast.makeText(this, "Datei nicht gefunden", Toast.LENGTH_SHORT)
						.show();
			} catch (IOException e) {
				Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
