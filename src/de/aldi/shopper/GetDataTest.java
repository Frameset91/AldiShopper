package de.aldi.shopper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GetDataTest extends Thread {

	public void getData(Context con) {

		//Erstellen der Notwendigen Objekte zum Daten schreiben
		AngebotsHelper AngebotsHelperObject = new AngebotsHelper(con);
		SQLiteDatabase db = AngebotsHelperObject.getWritableDatabase();

		//Abfrage des JSON Arrays der Ausgabe der Online Datenbank und Zuweisung der einzelnen Elemente in Strings.
		
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

			}

		} catch (JSONException e) {
			System.out.println("JSON Exception");
		}

		db.close();

	}

	
	 // Lädt anhand einer URL ein JSON Objekt
		private JSONArray getJsonArray(String URL) {
		// Initialisierung
		String JsonResponse = connect(URL, null);
		JSONArray json = null;

		try {
			json = (new JSONArray(JsonResponse)); // Konvertiert String nach JSON
													
		} catch (Exception e) {
					}

		return json;
	}

	
	 //Stellt eine HTTP Verbindung zu einer URL her und gibt den Inhalt zurück.
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
		
		} catch (IOException e) {
		
		}
		return null;
	}

	
	 //Wandelt den InputStream in einen String um.
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
	}

}
