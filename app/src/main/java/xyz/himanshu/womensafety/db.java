package xyz.himanshu.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by himanshusingh on 03/03/17.
 */
public class db extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="phonenumbers.db";
    private static final String TABLE_NAME="phoneNumber";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_PHONE="phonenumber";

    public db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+ TABLE_NAME + "(" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_PHONE +" TEXT "+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addnumber1(phone_number phone)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_PHONE,phone.get_phonenumber());
        SQLiteDatabase db=getWritableDatabase();
        onUpgrade(db,1,1);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void addnumber2(phone_number phone)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_PHONE, phone.get_phonenumber());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public String databaseToPhoneFirst()
    {
        String phoneNumber="";
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE 1";
        //cursor to move to first element
        Cursor s= db.rawQuery(query,null);
        s.moveToFirst();
        phoneNumber=s.getString(s.getColumnIndex("phonenumber"));
        return phoneNumber;
    }

    public String databaseToPhoneSecond()
    {
        String phoneNumber="";
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE 1";
        //cursor to move to first element
        Cursor s= db.rawQuery(query,null);
        s.moveToFirst();
        s.moveToNext();
        phoneNumber=s.getString(s.getColumnIndex("phonenumber"));
        return phoneNumber;
    }
    public int number()
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE 1";
        //cursor to move to first element
        Cursor s= db.rawQuery(query,null);
        if(s.getCount()==2)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
}
