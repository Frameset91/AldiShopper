package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProceedToCheckout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proceed_to_checkout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proceed_to_checkout, menu);
		return true;
	}

}
