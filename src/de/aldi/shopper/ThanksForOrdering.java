/**
 * Abschlie�ende Activity, die den bestellten Warenkorb in einem "Kassenbon" abbildet
 */
package de.aldi.shopper;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.TextView;

public class ThanksForOrdering extends Activity {

	private List<Product> cartList;
	private ThankYouAdapter orderAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks_for_ordering);
		
		// Hinweis, dass der Benutzer eine Email erh�lt, sobald der Wagen fertig ist
		AlertDialog.Builder thanks = new AlertDialog.Builder(this);
		thanks.setTitle("Vielen Dank f�r Ihre Bestellung!");
		thanks.setMessage("Sobald der Einkaufswagen f�r Sie bereit steht, erhalten Sie eine Email.");
		thanks.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		thanks.show();
		Intent checkout = getIntent();
		String total = checkout.getStringExtra("total");
		
		// Auslesen der gew�hlten Filiale
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView store = (TextView) findViewById(R.id.store);
		store.setText(userData.getString("store", ""));
		
		// Bestellung in Liste ausgeben
		cartList = CartHelper.getCartList();
		ListView orderList = (ListView) findViewById(R.id.orderList);
		orderAdapter = new ThankYouAdapter(cartList, getLayoutInflater());
		orderList.setAdapter(orderAdapter);
		
		// Gesamtpreis 
		TextView totalPrice = (TextView) findViewById(R.id.total);
		totalPrice.setText(total);
		
		TextView articles = (TextView) findViewById(R.id.articles);
		int articleQuant = 0;
		for(Product p : cartList){
			articleQuant += CartHelper.getProductQuantity(p);
		}
		articles.setText(Integer.toString(articleQuant)+ " Artikel");
	}
	
/**
 * bei Schlie�en der Activity, zur�ck zur MainActivity
 */
	@Override
	public void onBackPressed(){
		Intent main = new Intent(this, MainActivity.class);
		main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(main);
		
		//Aktuellen Warenkorb l�schen, wenn Bestellung abgeschickt wurde
		File dir = getFilesDir();
		File activeCart = new File(dir, "activeCart");
		activeCart.delete();
		CartHelper.removeAll();
	}

}
