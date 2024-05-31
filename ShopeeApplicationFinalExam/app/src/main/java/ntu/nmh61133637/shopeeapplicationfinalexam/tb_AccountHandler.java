package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class tb_AccountHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "tb_Account";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STUDENT_ID = "studentID";
    private static final String KEY_CLASS_NAME = "className";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    public tb_AccountHandler(Context context) {
        super(context, DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                                                   TABLE_NAME, KEY_ID, KEY_NAME, KEY_STUDENT_ID, KEY_CLASS_NAME, KEY_EMAIL, KEY_PHONE_NUMBER);
        db.execSQL(createAccountTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        String dropAccountTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropAccountTable);
        onCreate(db);
    }
    //Add Record
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(KEY_NAME, account.getName());
        item.put(KEY_STUDENT_ID, account.getStudentID());
        item.put(KEY_CLASS_NAME, account.getClassName());
        item.put(KEY_EMAIL, account.getEmail());
        item.put(KEY_PHONE_NUMBER, account.getPhoneNumber());
        db.insert(TABLE_NAME, null, item);
        db.close();
    }
    public Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(id) },null, null, null);
        Account account;
        if (cursor != null) {
            cursor.moveToFirst();
            account = new Account(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                                cursor.getString(3), cursor.getString(4), cursor.getString(5));
            cursor.close();
        } else account = new Account();
        return account;
    }
    public void editAccount(int id, Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(KEY_NAME, account.getName());
        item.put(KEY_STUDENT_ID, account.getStudentID());
        item.put(KEY_CLASS_NAME, account.getClassName());
        item.put(KEY_EMAIL, account.getEmail());
        item.put(KEY_PHONE_NUMBER, account.getPhoneNumber());
        db.update(TABLE_NAME, item, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                                        cursor.getString(3), cursor.getString(4), cursor.getString(5));
            accounts.add(account);
            cursor.moveToNext();
        }
        cursor.close();
        return accounts;
    }
}
