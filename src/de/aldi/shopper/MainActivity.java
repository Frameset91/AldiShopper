package de.aldi.shopper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void openNewcart(View view){
    	Intent newcart = new Intent(this, Newcart.class);
    	startActivity(newcart);
    }
    
    public void openActivecart(View view){
    	Intent activecart = new Intent(this, Activecart.class);
    	startActivity(activecart);
    }
    
    public void openOptions(View view){
    	Intent options = new Intent(this, Options.class);
    	startActivity(options);
    }
}
