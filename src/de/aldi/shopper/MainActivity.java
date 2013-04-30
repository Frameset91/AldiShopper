package de.aldi.shopper;

/**
 * wird beim Start der App aufgerufen
 */

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private SharedPreferences userData;	// Zur Speicherung des Vor- und Nachnamens
	private File dir;					// Speicherort im internen Speicher des Handys
	private File activeCart;			// Datei, in der der aktuelle Warenkorb gespeichert 
	Button btnActive;
	private Handler mHandler;
	private ProgressDialog dialog;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Überprüfen, ob bereits User-Daten hinterlegt wurden und ob Artikelliste noch aktuell ist, sonst neu abrufen
		userData = this.getSharedPreferences("userData", MODE_PRIVATE);
		SharedPreferences sp = this.getSharedPreferences("cacheData", MODE_PRIVATE);
		Date tmpLastDate = new Date(sp.getLong("cacheDate", 0));
		Calendar cal = Calendar.getInstance();
		cal.setTime(tmpLastDate);
		cal.add(Calendar.DAY_OF_YEAR, +1);
		Date lastDate = cal.getTime();
		
		if(lastDate.before(new Date())) {
			dialog = ProgressDialog.show(this, "",
					"Artikel werden abgerufen!", true);
			mHandler = new Handler();
			loadData.start();
			
			SharedPreferences.Editor spe = sp.edit();
			spe.putLong("cacheDate", System.currentTimeMillis());
			spe.commit();
		}
		
		if (userData.getString("firstname", null) == null){
			Intent optionsFirst = new Intent(this, Options.class);
			startActivity(optionsFirst);
			}
		// Wenn bereits die Namen gespeichert wurden, Main aufrufen
		setContentView(R.layout.activity_main);
		dir = getFilesDir();
		activeCart = new File(dir, "activeCart");
		if (activeCart.exists() == false){		// Überprüfe, ob bereits ein aktueller Cart vorhanden ist. Falls nein, Button Aktueller Warenkorb deaktivieren
			btnActive = (Button) findViewById(R.id.btnActive);
			btnActive.setEnabled(false);
		}			
	}
	
	private Thread loadData = new Thread() {
		public void run() {

			GetDataTest gdt = new GetDataTest();
			
			gdt.getData(MainActivity.this);
			
			mHandler.post(showUpdate);

		}
	};
	
	private Runnable showUpdate = new Runnable() {
		public void run() {
			dialog.dismiss();
		}
	};
	
	/**
	 * bei jedem Aufruf der MainActivity wird überprüft, ob ein aktueller Warenkorb gespeichert ist oder nicht.
	 * Falls ja, wird der Button "aktueller Warenkorb" deaktiviert.
	 */
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

	public void openNewcart(View view) {
		final Intent newcart = new Intent(this, Newcart.class);

		if (activeCart.exists()==false)
			startActivity(newcart);
		
		// Wenn bereits ein aktueller Warenkorb vorhanden ist, wird ein Dialog geöffnet, 
		// bei dem der Benutzer den aktuellen Warenkorb löschen kann, oder Abbruch
		else {
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);
			myAlertDialog.setTitle("Hinweis");
			myAlertDialog.setMessage("Es gibt bereits einen aktuellen Warenkorb. Löschen? ");
			myAlertDialog.setPositiveButton("Ja",new DialogInterface.OnClickListener() {
						// Wenn der Benutzer "Ja" auswählt, wird der aktuelle Warenkorb gelöscht und kann direkt einen neuen anlegen
						public void onClick(DialogInterface arg0, int arg1) {
							activeCart.delete();
							Button btnActive = (Button) findViewById(R.id.btnActive);
							btnActive.setEnabled(false);
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

	//Testabruf für Daten
//	public void openData(View view) {
//		Intent data = new Intent(this, GetDataTest.class);
//		startActivity(data);
//	}

}
