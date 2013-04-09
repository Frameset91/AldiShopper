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
	private File activeCart;	// Unsere Datei, in der aktuelle Warenkörbe gespeichert werden

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Zuerst überprüfen, ob bereits User-Daten hinterlegt wurden
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		// Wenn bereits die Namen gespeichert wurden, Main aufrufen
		if (userData.getString("firstname", null) != null){
			setContentView(R.layout.activity_main);
			dir = getFilesDir();
			activeCart = new File(dir, "activeCart");
			if (activeCart.exists() == false){		// Überprüfe, ob bereits ein aktueller Cart vorhanden ist. Falls nein, Button Aktueller Warenkorb deaktivieren
				Button btnActive = (Button) findViewById(R.id.btnActive);
				btnActive.setEnabled(false);
			}
			
		}
		
		// Wenn noch nichts eingegeben, erst Optionen aufrufen
		else {
			Intent optionsFirst = new Intent(this, Options.class);
			startActivity(optionsFirst);
		}
	}
	
//	@Override
//	protected void onResume(){
//		super.onResume();
//		Button btnActive = (Button) findViewById(R.id.btnActive);
//		if (activeCart.exists()==false)
//			btnActive.setEnabled(false);
//		else btnActive.setEnabled(true);
//	}

	public void openNewcart(View view) {
		final Intent newcart = new Intent(this, Newcart.class);

		// Wenn bereits ein aktueller Warenkorb vorhanden ist, wird ein Dialog geöffnet, bei dem der Benutzer den aktuellen Warenkorb löschen kann, oder Abbruch
		if (activeCart.exists()==false)
		//if (CartHelper.getCartList().isEmpty()==true)
			startActivity(newcart);
		
		else {
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);
			myAlertDialog.setTitle("Hinweis");
			myAlertDialog.setMessage("Es gibt bereits einen aktuellen Warenkorb. Löschen? ");
			myAlertDialog.setPositiveButton("Ja",new DialogInterface.OnClickListener() {
						// Wenn der Benutzer "Ja" auswählt, wird der aktuelle Warenkorb gelöscht und kann direkt einen neuen anlegen
						public void onClick(DialogInterface arg0, int arg1) {
							activeCart.delete();
							startActivity(newcart);
						}
			});
			myAlertDialog.setNegativeButton("Nein",	new DialogInterface.OnClickListener() {
						//Wenn der Benutzer nein auswählt, wird nichts getan, der Dialog schließt sich
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
