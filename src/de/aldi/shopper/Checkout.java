package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import de.aldi.shopper.Options;

public class Checkout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		
		Options name = new Options();
		TextView fName = (TextView)findViewById(R.id.textView2);
		TextView LName = (TextView)findViewById(R.id.textView1);
		
		fName.setText(name.getFirstname());
		LName.setText(name.getLastname());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
