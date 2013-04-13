package de.aldi.shopper;

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
		
		//TODO Email-Weiterleitung?
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("message/rfc822");
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"nikey@hotmail.de"});
		email.putExtra(Intent.EXTRA_SUBJECT, "Bestellung");
		email.putExtra(Intent.EXTRA_TEXT, "Test-Email der ersten Bestellung");
		try{
			startActivity(Intent.createChooser(email, "Send mail..."));
		}
		catch(android.content.ActivityNotFoundException ex){
			Toast.makeText(this, "no email clients installed", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onBackPressed(){
		Intent main = new Intent(this, MainActivity.class);
		startActivity(main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanks_for_ordering, menu);
		return true;
	}

}
