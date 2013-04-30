package de.aldi.shopper;

/**
 * Zum Überprüfen der Benutzereingaben und zum endgültigen Abschicken der Bestellung
 */

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Checkout extends Activity {
	
	private String total;
	private String userFirstname;
	private String userLastname;
	private SharedPreferences userData;
	final int ACTIVITY_ID = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		// Füllen von "Summe" durch Übergabe des Wertes aus Proceed
		Intent proceedToCheckout = getIntent();
		total = proceedToCheckout.getStringExtra("total");
		TextView textTotal = (TextView) findViewById(R.id.total);
		List<Product> list = CartHelper.getCartList();
		int totalQuant = 0;
		for (Product p : list){
			totalQuant += CartHelper.getProductQuantity(p);
		}
		textTotal.setText(total + "     "+ "(" + totalQuant + " Artikel)");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//Füllen der "Bestellung für: " mit den eingetragenen Benutzerdaten
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView fName = (TextView)findViewById(R.id.firstname);
		TextView lName = (TextView)findViewById(R.id.lastname);
		userFirstname = userData.getString("firstname", "");
		fName.setText(userFirstname);
		userLastname = userData.getString("lastname", ""); 
		lName.setText(userLastname);
		
		// Füllen der Stammfiliale
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		ArrayAdapter<CharSequence> spinAd = ArrayAdapter.createFromResource(this, R.array.stores, android.R.layout.simple_spinner_item);
		spinAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		store.setAdapter(spinAd);
		int pos = spinAd.getPosition(userData.getString("store", ""));
		store.setSelection(pos);
	}
	
	/**
	 * Absenden der Bestellung
	 */
	public void onSendOrder(View view){
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		SharedPreferences.Editor userDataEditor = userData.edit();
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		String chosenStore = store.getSelectedItem().toString();
		userDataEditor.putString("store", chosenStore);
		userDataEditor.apply();
		
		// Öffnen des Auswahldialogs für das Absenden der Email
		// und vorbereiten der Email (Artikelliste, Menge, Filiale, Name)
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("message/rfc822");
		//Beispiel-Empfänger; Für jede Filiale wird eine Email-Adresse hinterlegt, die hier eingefügt wird
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"nora.herentrey@googlemail.com"});
		email.putExtra(Intent.EXTRA_SUBJECT, "Bestellung an Filiale: " + chosenStore);		
		List<Product> orderList = CartHelper.getCartList();
		String emailList = "Bestellung für : " + userLastname + ", " + userFirstname + "\n\n";
		for(Product p : orderList){
			String name = p.getName();
			int quantity = CartHelper.getProductQuantity(p);
			emailList = emailList + name + ", x" + quantity + "\n";
		}
		emailList = emailList + "\n Summe: " + total;
		email.putExtra(Intent.EXTRA_TEXT, emailList);

		Intent eStart = Intent.createChooser(email, "Senden");
		eStart.putExtra("list", email);
		startActivityForResult(eStart, ACTIVITY_ID);
	}
	
	public void onEditOptions(View view){
		Intent openOp = new Intent(this, Options.class);
		startActivity(openOp);
	} 
	
	// Wenn der Email-Dialog geschlossen wird, wird die entgültige Activity gestartet
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Intent thankYou = new Intent(this, ThanksForOrdering.class);
		thankYou.putExtra("total", total);
		startActivity(thankYou);
	}

}
