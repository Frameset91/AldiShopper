/**
 * Zum F�llen der Exandable List der Produkte mit Daten
 */

package de.aldi.shopper;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ProductAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> comGroup;
	private ArrayList<ArrayList<Product>> products;
	private LayoutInflater inflater;

	public ProductAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<Product>> products) {
		this.comGroup = groups;
		this.products = products;
		inflater = LayoutInflater.from(context);
	}

	public Product getChild(int groupPosition, int childPosition) {
		return products.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return (long) (groupPosition * 50 + childPosition); // Max 50 children
															// per group
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.explist_child, parent, false);

		Product prod = (Product) getChild(groupPosition, childPosition);
		
		TextView name = (TextView) v.findViewById(R.id.childname);
		if (name != null)
			name.setText(prod.getName());
		TextView unit = (TextView) v.findViewById(R.id.unit);
		if (unit != null)
			unit.setText(prod.getUnit());
		TextView price = (TextView) v.findViewById(R.id.price);
		if (price != null)
			price.setText(prod.getPrice() + " �");
		TextView descr = (TextView) v.findViewById(R.id.description);
		if (descr != null)
			descr.setText(prod.getDescription());
		
		NumberPicker quantity = (NumberPicker) v.findViewById(R.id.quantity);
		quantity.setMinValue(0); quantity.setMaxValue(40);
		//if (quantity != null){
		//	quantity.setValue(prod.getQuantity());
		//	}
		// CheckBox cb = (CheckBox)v.findViewById( R.id.check1 );
		// cb.setChecked( c.getState() );
		return v;
	}

	public int getChildrenCount(int groupPosition) {
		return products.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return comGroup.get(groupPosition);
	}

	public int getGroupCount() {
		return comGroup.size();
	}

	public long getGroupId(int groupPosition) {
		return (long) (groupPosition * 50); // To be consistent with getChildId
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.explist_group, parent, false);
		String gt = (String) getGroup(groupPosition);
		TextView colorGroup = (TextView) v.findViewById(R.id.groupname);
		if (gt != null)
			colorGroup.setText(gt);
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