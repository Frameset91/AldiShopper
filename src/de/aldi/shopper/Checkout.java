package de.aldi.shopper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends Activity {
	
	private String total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		Intent proceedToCheckout = getIntent();
		total = proceedToCheckout.getStringExtra("total");
		//Füllen der "Bestellung für: " mit den eingetragenen Benutzerdaten
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView fName = (TextView)findViewById(R.id.firstname);
		TextView lName = (TextView)findViewById(R.id.lastname);
		fName.setText(userData.getString("firstname", ""));
		lName.setText(userData.getString("lastname", ""));
		
		// Füllen der Stammfiliale
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		ArrayAdapter<CharSequence> spinAd = ArrayAdapter.createFromResource(this, R.array.stores, android.R.layout.simple_spinner_item);
		spinAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		store.setAdapter(spinAd);
		int pos = spinAd.getPosition(userData.getString("store", ""));
		store.setSelection(pos);
		
		// Füllen von "Summe" durch Übergabe aus Proceed
		TextView textTotal = (TextView) findViewById(R.id.total);
		textTotal.setText(total + "     "+ "(" + CartHelper.getCartList().size() + " Artikel)");
	}
	
	public void onSendOrder(View view){
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		SharedPreferences.Editor userDataEditor = userData.edit();
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		String chosenStore = store.getSelectedItem().toString();
		userDataEditor.putString("store", chosenStore);
		userDataEditor.apply();
		
		//TODO Email-Weiterleitung?
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("message/rfc822");
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"nora.herentrey@gmail.com"});
		email.putExtra(Intent.EXTRA_SUBJECT, "Einkaufswagenbestellung an Filiale " + chosenStore);
		email.putExtra(Intent.EXTRA_TEXT, "Test-Email der ersten Bestellung");
		try{
			startActivity(Intent.createChooser(email, "Bestellung absenden"));
		}
		catch(android.content.ActivityNotFoundException ex){
			Toast.makeText(this, "Keine Email-Applikation installiert!", Toast.LENGTH_LONG).show();
		}
		
		Intent thankYou = new Intent(this, ThanksForOrdering.class);
		thankYou.putExtra("total", total);
		startActivity(thankYou);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
