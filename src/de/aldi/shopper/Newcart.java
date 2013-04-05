package de.aldi.shopper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;


public class Newcart extends ExpandableListActivity{
	
    private ProductAdapter catalog;
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
        comGroupsList.add("Käse");
	    comGroupsList.add("Müsli");
	    comGroupsList.add("Kaffee");
	    
        comGroups = new ArrayList<ArrayList<Product>>(); //Liste Warengruppe, die Listen mit Produkten speichert
        
        product = new ArrayList<Product>(); //Produktliste für jeweils eine Warengruppe
        // 1. Kindgruppe
        product.add( new Product(33, 3, "ALPENMARK Reibekäse", "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet", "200g", 1.19, 0,  false)); 
        comGroups.add(product);
        
        //2. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(1, 2, "KNUSPERONE Honey Wheat","Lorem ipsum dolor sit amet", "750g", 1.99, 1, false)); 
        product.add( new Product(27, 1, "KNUSPERONE Nougat Bits", "Lorem ipsum dolor sit amet","500g", 1.99, 2, false));
        comGroups.add(product);
        
        //3. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(33, 1, "AMAROY Premium Röstkaffee Extra Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.59, 3, false)); 
        product.add( new Product(1, 2, "AMAROY Premium Röstkaffee Entkoffeiniert Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.99, 4, false)); 
        product.add( new Product(27, 1, "AMAROY Premium Röstkaffee Extra Gemahlen", "Lorem ipsum dolor sit amet","500g", 2.99, 5, false));
        comGroups.add(product);

		catalog = new ProductAdapter(this,comGroupsList, comGroups);
		setListAdapter( catalog );
		NumberPicker quantPick = (NumberPicker) findViewById(R.id.quantity);
    }

    public void onContentChanged  () {
        super.onContentChanged();
    }
    
//	muss ersetzt werden: Anzeigen der Zwischensumme
//    @Override
//    public  boolean onChildClick(ExpandableListView parent, View v, int groupPosition,int childPosition, long id) {
//        System.out.println("auf Kind geklickt: " + groupPosition + ", " + childPosition);
//        
//        Product curProd = catalog.getChild(groupPosition, childPosition);
//        double subTotal = curProd.getPrice();
//        
//        //Zwischensumme
//        //double subTotal = 0;
//        //for (Product p : comGroups.get(childPosition)){
//        //	double prodPrice = p.getPrice();
//        //	subTotal = p.price; //* quantity
//        //	}
//        
//        TextView prodprice = (TextView)findViewById(R.id.Subtotal);
//        prodprice.setText("Zwischensumme: " + subTotal + "€");
//        //CheckBox cb = (CheckBox)v.findViewById( R.id.check1 );
//        //if( cb != null )
//        //   cb.toggle();
//        return false;
//    }
    

    public void onProceed(View view){
    	Intent proceed = new Intent(this, ProceedToCheckout.class);
    	startActivity(proceed);
    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newcart, menu);
		return true;
	}

}
