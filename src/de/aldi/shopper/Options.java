/**
 * Eingeben der Benutzerdaten: Name, Filiale (es folgt: Bankverbindung etc. zum direkten Abbuchen)
 */

package de.aldi.shopper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Options extends Activity {
	
	private SharedPreferences userData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		// Abrufen der SharedPreferences mit den Benutzerdaten
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		EditText fName = (EditText) findViewById(R.id.fistname);
		EditText lName = (EditText) findViewById(R.id.lastname);
		fName.setText(userData.getString("firstname", ""));
		lName.setText(userData.getString("lastname", ""));
		
		// Abrufen der Filialen aus gespeichertem Array, Füllen in ein DropDown-Menü
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		ArrayAdapter<CharSequence> spinAd = ArrayAdapter.createFromResource(this, R.array.stores, android.R.layout.simple_spinner_item);
		spinAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		store.setAdapter(spinAd);
		int pos = spinAd.getPosition(userData.getString("store", ""));
		store.setSelection(pos);
	}

	public void onSaveSettings(View view) {
		EditText fName = (EditText) findViewById(R.id.fistname);
		EditText lName = (EditText) findViewById(R.id.lastname);
		Spinner store = (Spinner) findViewById(R.id.storeDD);
		
		// Sollen die Daten gespeichert werden, aber es wurden keine Werte gesetzt -> Hinweis öffnen
		if(fName.getText().toString().matches("")|| lName.getText().toString().matches("")){
			Toast setData = Toast.makeText(this, "Bitte geben Sie ihre Daten ein!", Toast.LENGTH_SHORT);
			setData.setGravity(Gravity.CENTER, 0, 0);
			setData.show();
		}
		
		// Wurden Daten eingegeben, diese lokal im Handy speichern, Activity beenden
		else{
			SharedPreferences.Editor userDataEditor = userData.edit();
			userDataEditor.putString("firstname", fName.getText().toString());
			userDataEditor.putString("lastname", lName.getText().toString());
			userDataEditor.putString("store", store.getSelectedItem().toString());
			userDataEditor.apply();
			finish();
			}
		}

}
