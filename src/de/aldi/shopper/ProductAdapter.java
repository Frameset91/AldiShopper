/**
 * Zum F�llen der Exandable List der Produkte mit Daten
 */

package de.aldi.shopper;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class ProductAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> comGroupsList;
	private ArrayList<ArrayList<Product>> comGroups;
	private LayoutInflater inflater;
	private HashMap<Integer, Integer> loadedCart = new HashMap<Integer, Integer>();

	public ProductAdapter(Context context, ArrayList<String> comGroupsList, ArrayList<ArrayList<Product>> comGroups) {
		this.comGroupsList = comGroupsList;
		this.comGroups = comGroups;
		
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		try {
			fileIn = context.openFileInput("activeCart");
		
			Map<Product, Integer> loadedCartMap;
			objectIn = new ObjectInputStream(fileIn);
			loadedCartMap = (Map<Product, Integer>) objectIn.readObject();
			for (Product p : loadedCartMap.keySet()){
				int quantity = loadedCartMap.get(p);
				loadedCart.put(p.getID(), quantity);
			}
		} catch (Exception e) {
		}
		
		inflater = LayoutInflater.from(context);
	}
	

	public Product getChild(int groupPosition, int childPosition) {
		return comGroups.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return (long) (groupPosition * 50 + childPosition); // Max 50 children
															// per group
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.explist_child, parent, false);

		final Product prod = (Product) getChild(groupPosition, childPosition); // F�r jedes Produkt werden die Daten geholt
		
		TextView name = (TextView) v.findViewById(R.id.childname);
		if (name != null)
			name.setText(prod.getName());
		TextView unit = (TextView) v.findViewById(R.id.unit);
		if (unit != null)
			unit.setText(prod.getUnit());
		final TextView price = (TextView) v.findViewById(R.id.price);
		if (price != null)
			price.setText(prod.getPrice() + " �");
		TextView descr = (TextView) v.findViewById(R.id.description);
		if (descr != null)
			descr.setText(prod.getDescription());
		final NumberPicker numPickQuantity = (NumberPicker) v.findViewById(R.id.quantity);
		numPickQuantity.setMinValue(0); numPickQuantity.setMaxValue(40);
		if(CartHelper.getCartList().contains(prod) == true)
			numPickQuantity.setValue(CartHelper.getProductQuantity(prod));
		else if((loadedCart.get(prod.getID())) != null)
			numPickQuantity.setValue( loadedCart.get(prod.getID()) );
		else numPickQuantity.setValue(0);
		
		// NumberPicker zum Ausw�hlen der Mengen und Speichern in CartHelper
		numPickQuantity.setOnValueChangedListener(new OnValueChangeListener() {			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				int quantity = numPickQuantity.getValue();
				CartHelper.setQuantity(prod, quantity);
				System.out.println(prod.getName() + " wurde gew�hlt, Menge: " + quantity+"\n in CartHelper gespeichert");
			}
		});
		return v;
	}
	
	public int getChildrenCount(int groupPosition) {
		return comGroups.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return comGroupsList.get(groupPosition);
	}

	public int getGroupCount() {
		return comGroupsList.size();
	}
	
	public long getGroupId(int groupPosition) {
		return (long) (groupPosition * 50);
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.explist_group, parent, false);
		String gt = (String) getGroup(groupPosition);
		TextView comGroup = (TextView) v.findViewById(R.id.groupname);
		if (gt != null)
			comGroup.setText(gt);
		return v;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void onGroupCollapsed(int groupPosition) {
	}

	public void onGroupExpanded(int groupPosition) {
	}


}
