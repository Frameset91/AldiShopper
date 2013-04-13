package de.aldi.shopper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class Newcart extends ExpandableListActivity {

	private ProductAdapter prodAdapterCatalog;
	private ArrayList<String> comGroupsList;
	ArrayList<ArrayList<Product>> comGroups;
	ExpandableListView expListCatalog;
	ArrayList<Product> product;

	// private Button btnDone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcart);

		// TODO button ausblenden, wenn noch nichts eingetragen ist!! wieder
		// aktivieren funktioniert nicht
		// if(CartHelper.getCartList().isEmpty()){
		// btnDone = (Button) findViewById(R.id.btnProceed);
		// btnDone.setEnabled(false);
		// }

		// Liste für Warengruppen
		comGroupsList = new ArrayList<String>();
		comGroups = new ArrayList<ArrayList<Product>>(); // Liste Warengruppe, die Listen mit Produkten speichert
		SQLiteDatabase db = new AngebotsHelper(this).getReadableDatabase();

		Cursor cur = AngebotsHelper.getCursorCategory(db);

		cur.moveToFirst();
		do {
			comGroupsList.add(cur.getString(0));
			product = new ArrayList<Product>(); // Produktliste für jeweils eine Warengruppe
			Cursor cur2 = AngebotsHelper.getCursorCategoryItem(db, cur.getString(1));
			cur2.moveToFirst();
			do {
				product.add(new Product(cur2.getInt(0), cur2.getInt(1), cur2.getString(2), cur2.getString(3), cur2.getString(4), cur2.getDouble(5), false));
			} while (cur2.moveToNext());
			comGroups.add(product);
		} while (cur.moveToNext());

		// comGroupsList.add("Käse");
		// comGroupsList.add("Müsli");
		// comGroupsList.add("Kaffee");
		//
		// comGroups = new ArrayList<ArrayList<Product>>(); //Liste Warengruppe, die Listen mit Produkten speichert
		//
		// product = new ArrayList<Product>(); //Produktliste für jeweils eine Warengruppe
		//
		// // 1. Kindgruppe
		// product.add( new Product(1, 1, "ALPENMARK Reibekäse", "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet", "200g", 1.19, false));
		// comGroups.add(product);
		//
		// //2. Kindgruppe
		// product = new ArrayList<Product>();
		// product.add( new Product(2, 2, "KNUSPERONE Honey Wheat", "Lorem ipsum dolor sit amet", "750g", 1.99, false));
		// product.add( new Product(3, 3, "KNUSPERONE Nougat Bits", "Lorem ipsum dolor sit amet","500g", 1.99, false));
		// comGroups.add(product);
		//
		// //3. Kindgruppe
		// product = new ArrayList<Product>();
		// product.add( new Product(4, 4, "AMAROY Premium Röstkaffee Extra Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.59, false));
		// product.add( new Product(5, 5, "AMAROY Premium Röstkaffee Entkoffeiniert Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.99, false));
		// product.add( new Product(6, 6, "AMAROY Premium Röstkaffee Extra Gemahlen", "Lorem ipsum dolor sit amet","500g", 2.99, false));
		// comGroups.add(product);

		prodAdapterCatalog = new ProductAdapter(this, comGroupsList, comGroups);
		setListAdapter(prodAdapterCatalog);
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}

	/**
	 * Go to next activity: ProceedToCheckout
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
	 * delete selected quantities if user goes back to MainActivity
	 */

	@Override
	public void onBackPressed() {
		CartHelper.removeAll();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newcart, menu);
		return true;
	}

}
