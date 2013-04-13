package de.aldi.shopper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		Intent proceedToCheckout = getIntent();
		total = proceedToCheckout.getStringExtra("total");
		// F�llen von "Summe" durch �bergabe aus Proceed
		TextView textTotal = (TextView) findViewById(R.id.total);
		textTotal.setText(total + "     "+ "(" + CartHelper.getCartList().size() + " Artikel)");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//F�llen der "Bestellung f�r: " mit den eingetragenen Benutzerdaten
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView fName = (TextView)findViewById(R.id.firstname);
		TextView lName = (TextView)findViewById(R.id.lastname);
		userFirstname = userData.getString("firstname", "");
		fName.setText(userFirstname);
		userLastname = userData.getString("lastname", ""); 
		lName.setText(userLastname);
		
		// F�llen der Stammfiliale
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		ArrayAdapter<CharSequence> spinAd = ArrayAdapter.createFromResource(this, R.array.stores, android.R.layout.simple_spinner_item);
		spinAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		store.setAdapter(spinAd);
		int pos = spinAd.getPosition(userData.getString("store", ""));
		store.setSelection(pos);
	}
	
	public void onSendOrder(View view){
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		SharedPreferences.Editor userDataEditor = userData.edit();
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		String chosenStore = store.getSelectedItem().toString();
		userDataEditor.putString("store", chosenStore);
		userDataEditor.apply();
		
		//TODO Email-Weiterleitung?
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("message/rfc822");
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"nora.herentrey@googlemail.com"});
		email.putExtra(Intent.EXTRA_SUBJECT, "Bestellung an Filiale: " + chosenStore);		
		List<Product> orderList = CartHelper.getCartList();
		String emailList = "Bestellung f�r : " + userLastname + ", " + userFirstname + "\n\n";
		for(Product p : orderList){
			String name = p.getName();
			int quantity = CartHelper.getProductQuantity(p);
			emailList = emailList + name + ", x" + quantity + "\n";
		}
		emailList = emailList + "\n Summe: " + total;
		email.putExtra(Intent.EXTRA_TEXT, emailList);

		Intent eStart = Intent.createChooser(email, "Senden");
		eStart.putExtra("list", email);
		startActivity(eStart);
		
		Timer timer = new Timer();
		final Intent thankYou = new Intent(this, ThanksForOrdering.class);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				thankYou.putExtra("total", total);
				startActivity(thankYou);	
			}
		}, 10000);
	}
	
	public void onEditOptions(View view){
		Intent openOp = new Intent(this, Options.class);
		startActivity(openOp);
	}

}
