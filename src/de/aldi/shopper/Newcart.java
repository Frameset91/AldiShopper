package de.aldi.shopper;

/**
 * Activity zum Anlegen eines neuen Einkaufswagens
 */

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class Newcart extends ExpandableListActivity {

	private ProductAdapter prodAdapterCatalog;
	private ArrayList<String> comGroupsList;
	ArrayList<ArrayList<Product>> comGroups;
	ExpandableListView expListCatalog;
	ArrayList<Product> product;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcart);

		// Liste für Warengruppen
		comGroupsList = new ArrayList<String>();
		comGroups = new ArrayList<ArrayList<Product>>(); // Liste Warengruppe, die Listen mit Produkten speichert
		SQLiteDatabase db = new AngebotsHelper(this).getReadableDatabase(); //Lesezugriff auf die Datenbank

		Cursor cur = AngebotsHelper.getCursorCategory(db);//Cursor erzeugen zur Datenabfrage

		//Cursor läuft immer solange bis er am Ende seiner Liste ist. Einzelne Cursor für die unterschiedlichen Abfragen. Zahl entspricht Spalte.
		cur.moveToFirst();
		do {
			comGroupsList.add(cur.getString(0));
			product = new ArrayList<Product>(); // Produktliste für jeweils eine Warengruppe
			Cursor cur2 = AngebotsHelper.getCursorCategoryItem(db, cur.getString(1));
			cur2.moveToFirst();
			do {
				product.add(new Product(cur2.getInt(0), cur2.getInt(1), cur2.getString(2), cur2.getString(3), cur2.getString(4), cur2.getDouble(5)));
			} while (cur2.moveToNext());
			comGroups.add(product);
		} while (cur.moveToNext());

		prodAdapterCatalog = new ProductAdapter(this, comGroupsList, comGroups);
		setListAdapter(prodAdapterCatalog);
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}

	/**
	 * Zur nächsten Activity wechseln: ProceedToCheckout
	 */
	public void onProceed(View view) {
		if (CartHelper.getCartList().isEmpty())
			Toast.makeText(this, "Keine Artikel ausgewählt", Toast.LENGTH_SHORT)
					.show();
		else {
			Intent proceed = new Intent(this, ProceedToCheckout.class);
			startActivity(proceed);
		}
	}

	/**
	 * ausgewählte Mengen löschen, wenn der Benutzer zurück zur MainActivity geht
	 */

	@Override
	public void onBackPressed() {
		CartHelper.removeAll();
		Intent main = new Intent(this, MainActivity.class);
		startActivity(main);
	}

}
