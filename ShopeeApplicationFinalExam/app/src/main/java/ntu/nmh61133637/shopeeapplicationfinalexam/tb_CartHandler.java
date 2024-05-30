package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class tb_CartHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "tb_Cart";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_ID = "productID";
    private static final String KEY_QUANTITY = "quantity";

    public tb_CartHandler(Context context) {
        super(context, DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCartTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER)",
                                                   TABLE_NAME, KEY_ID, KEY_PRODUCT_ID, KEY_QUANTITY);
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        String dropCartTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropCartTable);
        onCreate(db);
    }
    //Add Record
    public void addProduct(int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(KEY_PRODUCT_ID, productID);
        item.put(KEY_QUANTITY, 1);
        db.insert(TABLE_NAME, null, item);
        db.close();
    }
    public void editProduct(int productID, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(KEY_PRODUCT_ID, productID);
        item.put(KEY_QUANTITY, quantity);
        db.update(TABLE_NAME, item, KEY_PRODUCT_ID + " = ?", new String[] { String.valueOf(productID) });
        db.close();
    }
    public void removeProduct(int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_PRODUCT_ID + " = ?", new String[] { String.valueOf(productID) });
        db.close();
    }
    public int getProductQuantity(int productID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_PRODUCT_ID + " = ?", new String[] { String.valueOf(productID) },null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int quantity = cursor.getInt(2);
            cursor.close();
            return quantity;
        } else return 0;
    }
    public ArrayList<Cart> getAllProduct() {
        ArrayList<Cart> products = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cart product = new Cart(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
}
