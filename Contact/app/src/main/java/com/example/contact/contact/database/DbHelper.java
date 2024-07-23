package com.example.contact.contact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.example.contact.contact.Register;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contact.db";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ORGANIZATION = "organization";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_WEBADDRESS = "webaddress";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_NICKNAME = "nickname";

    public static final String TABLE_CONTACT = "tbl_contact";

    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_PHONE + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_ORGANIZATION + " TEXT,"
            + KEY_ADDRESS + " TEXT,"
            + KEY_WEBADDRESS + " TEXT,"
            + KEY_NOTES + " TEXT,"
            + KEY_NICKNAME + " TEXT " + ")";


    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);

    }


    public long addConatct(Contact contact) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PHONE,contact.getPhone());
        values.put(KEY_EMAIL,contact.getEmail());
        values.put(KEY_ORGANIZATION,contact.getOrganization());
        values.put(KEY_ADDRESS,contact.getAddress());
        values.put(KEY_WEBADDRESS,contact.getWebaddress());
        values.put(KEY_NOTES,contact.getNotes());
        values.put(KEY_NICKNAME,contact.getNickname());
       long rel=db.insert(TABLE_CONTACT,null,values);

    return rel;
    }
}