package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;

public class ThanksForOrdering extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks_for_ordering);
		
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE); // Zum Auslesen der gewählten Filiale
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanks_for_ordering, menu);
		return true;
	}

}
