package de.aldi.shopper;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThanksForOrdering extends Activity {

	private List<Product> cartList;
	private ThankYouAdapter orderAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks_for_ordering);
		
		AlertDialog.Builder thanks = new AlertDialog.Builder(this);
		thanks.setTitle("Vielen Dank für Ihre Bestellung!");
		thanks.setMessage("Sobald der Einkaufswagen für Sie bereit steht, erhalten Sie eine Mitteilung.");
		thanks.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		thanks.show();
		Intent checkout = getIntent();
		String total = checkout.getStringExtra("total");
		
		// Auslesen der gewählten Filiale
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
	
	@Override
	public void onBackPressed(){
		Intent main = new Intent(this, MainActivity.class);
		main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(main);
		
		//Aktuellen Warenkorb löschen, wenn Bestellung abgeschickt wurde
		File dir = getFilesDir();
		File activeCart = new File(dir, "activeCart");
		activeCart.delete();
		CartHelper.removeAll();
	}

}
