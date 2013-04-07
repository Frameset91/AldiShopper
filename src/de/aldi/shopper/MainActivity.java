package de.aldi.shopper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private SharedPreferences userData; //Zur Speicherung des Vor- und Nachnamens
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        userData = this.getSharedPreferences("userData", MODE_PRIVATE);
        if(userData.getString("firstname", null) != null)	// Wenn bereits die Namen gespeichert wurden, Main aufrufen
        	setContentView(R.layout.activity_main);
        else {												// Wenn noch nichts eingegeben, erst Optionen aufrufen
        	setContentView(R.layout.activity_options);
        	final EditText firstname = (EditText) findViewById(R.id.fistname);
        	final EditText lastname = (EditText) findViewById(R.id.lastname);
        	Button btnSave = (Button) findViewById(R.id.saveSettings);
        	
        	btnSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				// Abspeichern der Namen
					SharedPreferences.Editor userDataEditor = userData.edit();
					userDataEditor.putString("firstname", firstname.getText().toString());
					userDataEditor.putString("lastname", lastname.getText().toString());
					userDataEditor.commit();
					setContentView(R.layout.activity_main);
				}
			});
        }
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
    
    public void openManualList(View view){
    	Intent manuallist = new Intent(this, ManualList.class);
    	startActivity(manuallist);
    }
    
    public void openData(View view){
    	Intent data = new Intent(this, GetDataTest.class);
    	startActivity(data);
    }
    
}
