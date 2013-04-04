package de.aldi.shopper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ManualList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_list);
		
		ListView shopping_list = (ListView)findViewById(R.id.shopping_list);
		final EditText enter_article = (EditText)findViewById(R.id.list_enter_article);
		final ArrayList<String> arrayList = new ArrayList<String>();
		final ArrayAdapter<String> adapterList;
		
		adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
		
		shopping_list.setAdapter(adapterList);
		
		Button addArticle = (Button) findViewById(R.id.list_add_article);
		addArticle.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				String item = enter_article.getText().toString();
				if(item != null){
					arrayList.add(0, enter_article.getText().toString());
					adapterList.notifyDataSetChanged();
					enter_article.setText("");
					}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_list, menu);
		return true;
	}

}
