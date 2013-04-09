package de.aldi.shopper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class Newcart extends ExpandableListActivity{
	
    private ProductAdapter prodAdapterCatalog;
    private ArrayList<String> comGroupsList;
    ArrayList<ArrayList<Product>> comGroups;
    ExpandableListView expListCatalog;
    ArrayList<Product> product;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcart);
//		
//		Button btnDone = (Button) findViewById(R.id.btnDone);
//		if(CartHelper.getCartList().isEmpty()){
//			btnDone.setEnabled(false);
//		}
		
		//TODO button ausblenden, wenn noch nichts eingetragen ist!!
		

        // Liste für Warengruppen
        comGroupsList = new ArrayList<String>();
        comGroupsList.add("Käse");
	    comGroupsList.add("Müsli");
	    comGroupsList.add("Kaffee");
	    
        comGroups = new ArrayList<ArrayList<Product>>(); //Liste Warengruppe, die Listen mit Produkten speichert
        
        product = new ArrayList<Product>(); //Produktliste für jeweils eine Warengruppe
        // 1. Kindgruppe
        product.add( new Product(1, 1, "ALPENMARK Reibekäse", "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet", "200g", 1.19, false)); 
        comGroups.add(product);
        
        //2. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(2, 2, "KNUSPERONE Honey Wheat","Lorem ipsum dolor sit amet", "750g", 1.99, false)); 
        product.add( new Product(3, 3, "KNUSPERONE Nougat Bits", "Lorem ipsum dolor sit amet","500g", 1.99, false));
        comGroups.add(product);
        
        //3. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(4, 4, "AMAROY Premium Röstkaffee Extra Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.59, false)); 
        product.add( new Product(5, 5, "AMAROY Premium Röstkaffee Entkoffeiniert Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.99, false)); 
        product.add( new Product(6, 6, "AMAROY Premium Röstkaffee Extra Gemahlen", "Lorem ipsum dolor sit amet","500g", 2.99, false));
        comGroups.add(product);

		prodAdapterCatalog = new ProductAdapter(this,comGroupsList, comGroups);
		setListAdapter( prodAdapterCatalog );
    }

    public void onContentChanged  () {
        super.onContentChanged();
//		Button btnDone = (Button) findViewById(R.id.btnDone);
//		if(CartHelper.getCartList().isEmpty())
//			btnDone.setEnabled(false);
//		else btnDone.setEnabled(true);
    }
    
    public void onProceed(View view){
    	Intent proceed = new Intent(this, ProceedToCheckout.class);
    	startActivity(proceed);
    }
    
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
