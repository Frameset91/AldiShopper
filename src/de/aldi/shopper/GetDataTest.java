package de.aldi.shopper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;

public class GetDataTest extends Activity {

	private Handler mHandler;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_data_test);

		dialog = ProgressDialog.show(GetDataTest.this, "",
				"Hier Lädt ein Dings!", true);
		mHandler = new Handler();
		loadData.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_data_test, menu);
		return true;
	}

	private Thread loadData = new Thread() {
		public void run() {

			AngebotsHelper AngebotsHelperObject = new AngebotsHelper(
					GetDataTest.this);
			SQLiteDatabase db = AngebotsHelperObject.getWritableDatabase();

			try {
				JSONArray jarr = getJsonArray("http://alexbusch.de/android/connect.php");

				for (int i = 0; i < jarr.length(); i++) {

					JSONObject tmpObj = jarr.getJSONObject(i);
					String productID = tmpObj.getString("productID");
					String name = tmpObj.getString("name");
					String categoryID = tmpObj.getString("categoryID");
					String cdesc = tmpObj.getString("cdesc");
					String description = tmpObj.getString("description");
					String unit = tmpObj.getString("unit");
					String price = tmpObj.getString("price");

					AngebotsHelper.insertProducts(db, productID, name,
							categoryID, cdesc, description, unit, price);

					// Log.i("Debug mich!!! "+i, tmpObj.getString("productID"));
				}

			} catch (JSONException e) {
				System.out.println("JSON Exception");
			}

			mHandler.post(showUpdate);
			db.close();

		}
	};

	private Runnable showUpdate = new Runnable() {
		public void run() {
			dialog.dismiss();
		}
	};

	/**
	 * Lädt anhand einer URL ein JSON Objekt
	 * 
	 * @param URL
	 *            URL als String die geladen werden soll
	 * @return JSONObject oder NULL wenn es einen Fehler gibt
	 */
	private JSONArray getJsonArray(String URL) {
		// Init
		String JsonResponse = connect(URL, null);
		JSONArray json = null;

		try {
			json = (new JSONArray(JsonResponse)); // Convert String to JSON
													// (screws Ordering of JSON)
		} catch (Exception e) {
			// Log.e("##############################################",
			// "UNIverse getJson Exception: "+URL);
		}

		return json;
	}

	/**
	 * Stelle eine HTTP Verbindung zu einer URL her und gibt den Inhalt zurück
	 * 
	 * @param url
	 *            Die URL
	 * @return
	 */
	public static String connect(String url, List<NameValuePair> postParams) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpPost httpType = new HttpPost(url);

		HttpResponse response;
		try {
			if (postParams != null)
				httpType.setEntity(new UrlEncodedFormEntity(postParams));

			response = httpclient.execute(httpType);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);

				instream.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			// Log.e("##############################################",
			// "UNIverse exception2: "+url);
		} catch (IOException e) {
			// Log.e("##############################################",
			// "UNIverse exception3: "+url);
		}
		return null;
	}

	/**
	 * Wandelt einen InputStream in einen String um. Diese Funktion versucht den
	 * Stream !komplett! auszulesen. Daher kann es nur für Datei oder URLs
	 * auslesen verwendet werden.
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	} // end method

	public boolean checkNetwork() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}
}
