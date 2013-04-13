package de.aldi.shopper;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private SharedPreferences userData; // Zur Speicherung des Vor- und Nachnamens
	private File dir;	// Unser Dateispeicher im internen Speicher des Handys
	private File activeCart;	// Unsere Datei, in der aktuelle Warenk�rbe gespeichert werden
	Button btnActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Zuerst �berpr�fen, ob bereits User-Daten hinterlegt wurden
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		// Wenn noch nichts eingegeben, erst Optionen aufrufen
		if (userData.getString("firstname", null) == null){
			Intent optionsFirst = new Intent(this, Options.class);
			startActivity(optionsFirst);
			}
		// Wenn bereits die Namen gespeichert wurden, Main aufrufen
		else {
			setContentView(R.layout.activity_main);
			dir = getFilesDir();
			activeCart = new File(dir, "activeCart");
			if (activeCart.exists() == false){		// �berpr�fe, ob bereits ein aktueller Cart vorhanden ist. Falls nein, Button Aktueller Warenkorb deaktivieren
				btnActive = (Button) findViewById(R.id.btnActive);
				btnActive.setEnabled(false);
			}
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		dir = getFilesDir();
		activeCart = new File(dir, "activeCart");
		if (activeCart.exists()==false){
			btnActive = (Button) findViewById(R.id.btnActive);
			if (btnActive != null)
				btnActive.setEnabled(false);
			}
		else{
			btnActive = (Button) findViewById(R.id.btnActive);
			if (btnActive != null)
				btnActive.setEnabled(true);
		}
	}
//TODO onResume: intent abfangen, bei newcart ein onBackPressed + intent anlegen

	public void openNewcart(View view) {
		final Intent newcart = new Intent(this, Newcart.class);

		// Wenn bereits ein aktueller Warenkorb vorhanden ist, wird ein Dialog ge�ffnet, bei dem der Benutzer den aktuellen Warenkorb l�schen kann, oder Abbruch
		if (activeCart.exists()==false)
		//if (CartHelper.getCartList().isEmpty()==true)
			startActivity(newcart);
		
		else {
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);
			myAlertDialog.setTitle("Hinweis");
			myAlertDialog.setMessage("Es gibt bereits einen aktuellen Warenkorb. L�schen? ");
			myAlertDialog.setPositiveButton("Ja",new DialogInterface.OnClickListener() {
						// Wenn der Benutzer "Ja" ausw�hlt, wird der aktuelle Warenkorb gel�scht und kann direkt einen neuen anlegen
						public void onClick(DialogInterface arg0, int arg1) {
							activeCart.delete();
							Button btnActive = (Button) findViewById(R.id.btnActive);
							btnActive.setEnabled(false);
							startActivity(newcart);
						}
			});
			myAlertDialog.setNegativeButton("Nein",	new DialogInterface.OnClickListener() {
						//Wenn der Benutzer nein ausw�hlt, wird nichts getan, der Dialog schlie�t sich
						public void onClick(DialogInterface arg0, int arg1) {}
			});
			myAlertDialog.show();
		}
	}

	public void openActivecart(View view) {
		Intent activecart = new Intent(this, Activecart.class);
		startActivity(activecart);
	}

	public void openOptions(View view) {
		Intent options = new Intent(this, Options.class);
		startActivity(options);
	}

	public void openManualList(View view) {
		Intent manuallist = new Intent(this, ManualList.class);
		startActivity(manuallist);
	}

	public void openData(View view) {
		Intent data = new Intent(this, GetDataTest.class);
		startActivity(data);
	}
	
	public void openTestThankYou(View view){
		Intent start = new Intent(this, ThanksForOrdering.class);
		startActivity(start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
