package de.aldi.shopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AngebotsHelper extends SQLiteOpenHelper{

	/*
	 * Festlegen der einzelenen Konstanten, welche im weiteren Verlauf genutzt werden.
	 */
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Waren.db";
	
	private static final String TABLE_NAME_PRODUCTS = "Products";
	private static final String PRODUCT_ID = "productID";
	private static final String PRODUCT_NAME = "name";
	private static final String PRODUCT_CATEGORY_ID = "categoryID";
	private static final String PRODUCT_CATEGORY_NAME = "cdesc";
	private static final String PRODUCT_DESCRIPTION = "description";
	private static final String PRODUCT_UNIT = "unit";
	private static final String PRODUCT_PRICE = "price";
	
	private static final String TABLE_CREATE_PRODUCTS =
				"CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCTS + " (" +
				PRODUCT_ID + " INTEGER PRIMARY KEY, " + 
				PRODUCT_NAME + " TEXT, " + 
				PRODUCT_CATEGORY_ID + " INTEGER, " +
				PRODUCT_CATEGORY_NAME + " TEXT, " +
				PRODUCT_DESCRIPTION + " TEXT, " +
				PRODUCT_UNIT + " TEXT, " +
				PRODUCT_PRICE + " REAL);";
				
				
	//Essenzieller Konstruktor ddes SQLiteOpenHelpers
	
    public AngebotsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Erstellen der Tabelle beim ersten Aufruf.
    
	@Override
	public void onCreate(SQLiteDatabase db) {
	db.execSQL(TABLE_CREATE_PRODUCTS);	
		
	}

	//Notwendige Methode des SQLiteOpenHelpers, wird aktuell nicht benötigt.
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
	}
	
	//Methode zum Einfügen der Produkte aus dem Internet in die SQLite DB
	
	public static long insertProducts(SQLiteDatabase db, final String productID, final String name, final String categoryID, final String cdesc, final String description, final String unit, final String price) {
		ContentValues values = new ContentValues();
		values.put(PRODUCT_ID, productID);
		values.put(PRODUCT_NAME, name);
		values.put(PRODUCT_CATEGORY_ID, categoryID);
		values.put(PRODUCT_CATEGORY_NAME, cdesc);
		values.put(PRODUCT_DESCRIPTION, description);
		values.put(PRODUCT_UNIT, unit);
		values.put(PRODUCT_PRICE, price);
		return (db.insert(TABLE_NAME_PRODUCTS, null, values));
	}
	
	/*
	 * Cursor zur Datenabfrage.
	 * Kategorienabfrage zur Erstellung der DropDown Kategorieauswahl.
	 */
	
	public static Cursor getCursorCategory(SQLiteDatabase db) {
		return db.query(true, TABLE_NAME_PRODUCTS, new String[] {PRODUCT_CATEGORY_NAME, PRODUCT_CATEGORY_ID}, null, null, null, null, null, null);
	}
	
	/*
	 * Cursor zur Datenabfrage.
	 * Abfragen der benötigten Werte.
	 */
	
	public static Cursor getCursorCategoryItem(SQLiteDatabase db, String categoryID) {
		final String selection = PRODUCT_CATEGORY_ID + "=?";
		final String[] selectionArgs = {categoryID};
		return db.query(TABLE_NAME_PRODUCTS, new String[] {PRODUCT_ID, PRODUCT_CATEGORY_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_UNIT, PRODUCT_PRICE}, selection, selectionArgs, null, null, null);
	}
	
}
