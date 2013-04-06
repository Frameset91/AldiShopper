package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Options extends Activity {

	public static String firstname;
	public static String lastname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	}

	public void onSaveSettings(View view) {
		EditText fName = (EditText) findViewById(R.id.fistname);
		EditText lName = (EditText) findViewById(R.id.lastname);
		firstname = fName.getText().toString();
		lastname = lName.getText().toString();
	}

	public String getFirstname() {
		if (firstname == null) {
			return "nicht eingegeben";
		}
		return firstname;
	}

	public String getLastname() {
		if (firstname == null) {
			return "nicht eingegeben";
		}
		return lastname;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

}
