package de.aldi.shopper;

/**
 * wird benötigt, um die Artikelliste mit Daten zu füllen und einem Layout zuzuweisen
 */


import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {

 private List<Product> mProductList;
 private LayoutInflater inflater;

 public CartAdapter(List<Product> list, LayoutInflater layoutInflater) {
  mProductList = list;
  inflater = layoutInflater;
 }

 @Override
 public int getCount() {
  return mProductList.size();
 }

 @Override
 public Object getItem(int position) {
  return mProductList.get(position);
 }

 @Override
 public long getItemId(int position) {
  return position;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
	 View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.proceed_child, parent, false);

		final Product prod = (Product) getItem(position);	// für jeden Artikel werden die Views mit Daten gefüllt
		
		TextView name = (TextView) v.findViewById(R.id.childname);
		if (name != null)
			name.setText(prod.getName());
		TextView unit = (TextView) v.findViewById(R.id.unit);
		if (unit != null)
			unit.setText(prod.getUnit());
		final TextView price = (TextView) v.findViewById(R.id.price);
		if (price != null)
			price.setText(prod.getPrice() + " €");
		TextView quant = (TextView) v.findViewById(R.id.quantity);
		quant.setText("Menge: "+ CartHelper.getProductQuantity(prod));

  return v;
 }

}
