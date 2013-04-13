package de.aldi.shopper;



import java.text.DecimalFormat;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThankYouAdapter extends BaseAdapter {

 private List<Product> mProductList;
 private LayoutInflater inflater;
 final DecimalFormat f = new DecimalFormat("#0.00");

 public ThankYouAdapter(List<Product> list, LayoutInflater layoutInflater) {
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
			v = inflater.inflate(R.layout.thankyou_child, parent, false);

		final Product prod = (Product) getItem(position);
		
		TextView name = (TextView) v.findViewById(R.id.product);
		if (name != null)
			name.setText(prod.getName());
		int prodQuantity = CartHelper.getProductQuantity(prod);
		TextView quant = (TextView) v.findViewById(R.id.quantity);
		quant.setText(prodQuantity + " x ");
		double prodPrice = prod.getPrice();
		TextView price = (TextView) v.findViewById(R.id.price);
		if (price != null){
			price.setText(f.format(prodPrice) +" €");
		}
		TextView priceTotal = (TextView) v.findViewById(R.id.priceTotal);
		double prodPriceTotal = prodQuantity * prodPrice;
		priceTotal.setText(f.format(prodPriceTotal) + " €");

  return v;
 }

}
