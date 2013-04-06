package de.aldi.shopper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
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


        // Liste f�r Warengruppen
        comGroupsList = new ArrayList<String>();
        comGroupsList.add("K�se");
	    comGroupsList.add("M�sli");
	    comGroupsList.add("Kaffee");
	    
        comGroups = new ArrayList<ArrayList<Product>>(); //Liste Warengruppe, die Listen mit Produkten speichert
        
        product = new ArrayList<Product>(); //Produktliste f�r jeweils eine Warengruppe
        // 1. Kindgruppe
        product.add( new Product(33, 3, "ALPENMARK Reibek�se", "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet", "200g", 1.19, false)); 
        comGroups.add(product);
        
        //2. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(1, 2, "KNUSPERONE Honey Wheat","Lorem ipsum dolor sit amet", "750g", 1.99, false)); 
        product.add( new Product(27, 1, "KNUSPERONE Nougat Bits", "Lorem ipsum dolor sit amet","500g", 1.99, false));
        comGroups.add(product);
        
        //3. Kindgruppe
        product = new ArrayList<Product>();
        product.add( new Product(33, 1, "AMAROY Premium R�stkaffee Extra Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.59, false)); 
        product.add( new Product(1, 2, "AMAROY Premium R�stkaffee Entkoffeiniert Kaffee-Pads", "Lorem ipsum dolor sit amet", "144g", 1.99, false)); 
        product.add( new Product(27, 1, "AMAROY Premium R�stkaffee Extra Gemahlen", "Lorem ipsum dolor sit amet","500g", 2.99, false));
        comGroups.add(product);

		prodAdapterCatalog = new ProductAdapter(this,comGroupsList, comGroups);
		setListAdapter( prodAdapterCatalog );
		
		
    }

    public void onContentChanged  () {
        super.onContentChanged();
    }
    
    
//	muss ersetzt werden: Anzeigen der Zwischensumme
    @Override
    public  boolean onChildClick(ExpandableListView parent, View v, int groupPosition,int childPosition, long id) {
        System.out.println("auf Kind geklickt: " + groupPosition + ", " + childPosition);
        
//        Product curProd = catalog.getChild(groupPosition, childPosition);
//        double subTotal = curProd.getPrice();
        
       // Zwischensumme
        double subTotal = 0;
        for (Product p : comGroups.get(childPosition)){
        	double prodPrice = p.getPrice();
        	subTotal = prodPrice * p.getQuantPicker().getValue();
        	}
        
        TextView prodprice = (TextView)findViewById(R.id.Subtotal);
        prodprice.setText("Zwischensumme: " + subTotal + "�");
        System.out.println(("Zwischensumme: " + subTotal + "�"));
        //CheckBox cb = (CheckBox)v.findViewById( R.id.check1 );
        //if( cb != null )
        //   cb.toggle();
        return false;
    }
    

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
