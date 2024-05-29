package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class tb_ProductHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "tb_Product";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SALE_COUNT = "saleCount";

    public tb_ProductHandler(Context context) {
        super(context, DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER)",
                                                   TABLE_NAME, KEY_ID, KEY_NAME, KEY_IMAGE, KEY_PRICE, KEY_SALE_COUNT);
        db.execSQL(createProductTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        String dropProductTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropProductTable);
        onCreate(db);
    }
    //Add Record
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(KEY_NAME, product.getName());
        item.put(KEY_IMAGE, product.getImage());
        item.put(KEY_PRICE, product.getPrice());
        item.put(KEY_SALE_COUNT, product.getSaleCount());
        db.insert(TABLE_NAME, null, item);
        db.close();
    }
    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(id) },null, null, null);
        Product product;
        if (cursor != null) {
            cursor.moveToFirst();
            product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
            cursor.close();
        } else product = new Product();
        return product;
    }
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
}
