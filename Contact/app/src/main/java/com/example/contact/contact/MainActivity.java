package com.example.contact.contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.contact.R;
import com.example.contact.contact.database.DbHelper;
import com.example.contact.contact.recycler_adpter.Contact_recycler;
import com.example.contact.contact.recycler_adpter.Recycler_Adpter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add;
    SQLiteDatabase db;
    DbHelper objdb;
    int[] id;
    String[] cname, cphone, cemail, corg, cadd, cwadd, cnote, cnick;


    private List<Contact_recycler> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Recycler_Adpter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        objdb = new DbHelper( MainActivity.this );
        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new Recycler_Adpter(contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareData();
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public void prepareData() {
        db = objdb.getReadableDatabase();
        List<Contact_recycler> list=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_contact", null);
        if (cursor.getCount() > 0) {
            id = new int[cursor.getCount()];
            cname = new String[cursor.getCount()];
            cphone = new String[cursor.getCount()];
            cemail = new String[cursor.getCount()];
            corg = new String[cursor.getCount()];
            cadd = new String[cursor.getCount()];
            cwadd = new String[cursor.getCount()];
            cnote = new String[cursor.getCount()];
            cnick = new String[cursor.getCount()];

            int i = 0;
            while (cursor.moveToNext()) {
                id[i] = cursor.getInt(0);
                cname[i] = cursor.getString(1);
                cphone[i] = cursor.getString(2);
                cemail[i] = cursor.getString(3);
                corg[i] = cursor.getString(4);
                cadd[i] = cursor.getString(5);
                cwadd[i] = cursor.getString(6);
                cnote[i] = cursor.getString(7);
                cnick[i] = cursor.getString(8);
                Contact_recycler bean = new Contact_recycler(id[i], cname[i], cphone[i], cemail[i], corg[i], cadd[i], cwadd[i], cnote[i], cnick[i]);
                contactList.add(bean);
                i++;


            }
            //        Contact_recycler con=new Contact_recycler("Ashwin","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con);
//
//        Contact_recycler con1=new Contact_recycler("ajaj","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con1);
//        Contact_recycler con3=new Contact_recycler("Ajay","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con3);
//
//        Contact_recycler con4=new Contact_recycler("aj","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con4);
//
//        Contact_recycler con5=new Contact_recycler("Ashwin","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con5);
//
//        Contact_recycler con6=new Contact_recycler("ajaj","706969380","ashwin@gmail.com","vccm","aaaaa","asasasas","asasas","asssa");
//        contactList.add(con6);
        }
    }
    private  List<Contact_recycler> contactList1()
    {
        List<Contact_recycler> list=new ArrayList<>();
        Cursor cursor1= getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.Contacts.DISPLAY_NAME+"ASC");
        cursor1.moveToFirst();
        while (cursor1.moveToNext())
        {
            contactList.add(new Contact_recycler(cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }
        return list;
    }
}