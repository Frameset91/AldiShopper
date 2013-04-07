package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;
import de.aldi.shopper.Options;

public class Checkout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		
		SharedPreferences userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		TextView fName = (TextView)findViewById(R.id.textView2);
		TextView lName = (TextView)findViewById(R.id.textView1);
		
		fName.setText(userData.getString("firstname", ""));
		lName.setText(userData.getString("lastname", ""));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
