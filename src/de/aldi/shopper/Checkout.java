package de.aldi.shopper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Checkout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		Intent proceedToCheckout = getIntent();
		String total = proceedToCheckout.getStringExtra("total");
		//Füllen der "Bestellung für: " mit den eingetragenen Benutzerdaten
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView fName = (TextView)findViewById(R.id.firstname);
		TextView lName = (TextView)findViewById(R.id.lastname);
		fName.setText(userData.getString("firstname", ""));
		lName.setText(userData.getString("lastname", ""));
		
		// Füllen von "Summe" durch Übergabe aus Proceed
		TextView textTotal = (TextView) findViewById(R.id.total);
		textTotal.setText(total + "     "+ "(" + CartHelper.getCartList().size() + " Artikel)");
		
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		ArrayAdapter<CharSequence> spinAd = ArrayAdapter.createFromResource(this, R.array.stores, android.R.layout.simple_spinner_item);
		spinAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		store.setAdapter(spinAd);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
