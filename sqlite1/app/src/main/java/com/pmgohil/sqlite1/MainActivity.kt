package com.pmgohil.sqlite1

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var id: EditText
    lateinit var name: EditText
    lateinit var age: EditText
    lateinit var ins: Button
    lateinit var sel: Button
    lateinit var upd: Button
    lateinit var del: Button
    lateinit var clr: Button
    lateinit var tbl: TableLayout
    private lateinit var sqLite: SQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.eid)
        name = findViewById(R.id.ename)
        age = findViewById(R.id.eage)
        ins = findViewById(R.id.ins)
        sel = findViewById(R.id.sel)
        upd = findViewById(R.id.upd)
        del = findViewById(R.id.del)
        clr = findViewById(R.id.clr)
        tbl = findViewById(R.id.tbl);
        sqLite = SQLite(this)

        ins.setOnClickListener() {
            val id: String = id.text.toString()
            val name: String = name.text.toString()
            val age: String = age.text.toString()
            if (id == "" && name == "" && age == "") {
                Toast.makeText(this, "Data is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val data: Boolean = sqLite.insertData(id, name, age)
                if (!data) {
                    Toast.makeText(
                        this, "Something is wrong in Database.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Record Inserted Successfully.", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        sel.setOnClickListener() {

            val data = sqLite.selData();
            if (data.count == 0) {
                Toast.makeText(this, "No data to fetch.", Toast.LENGTH_SHORT).show();
            } else {
                tbl.removeAllViews()
                val thr = TableRow(applicationContext);
                thr.setBackgroundColor(Color.rgb(58, 58, 56));
                val th1 = TextView(applicationContext);
                val th2 = TextView(applicationContext);
                val th3 = TextView(applicationContext);
                th1.setTextColor(Color.parseColor("#bdbdbd"))
                th2.setTextColor(Color.parseColor("#bdbdbd"));
                th3.setTextColor(Color.parseColor("#bdbdbd"));
                th1.setPadding(30, 30, 30, 30);
                th2.setPadding(30, 30, 30, 30);
                th3.setPadding(30, 30, 30, 30);
                th1.text = "Id";
                th2.text = "Name";
                th3.text = "Age";
                thr.addView(th1);
                thr.addView(th2);
                thr.addView(th3);
                tbl.addView(thr);
                while (data.moveToNext()) {
                    val tr = TableRow(applicationContext);
                    tr.setBackgroundColor(Color.rgb(150, 145, 56))
                    val tv1 = TextView(applicationContext);
                    val tv2 = TextView(applicationContext);
                    val tv3 = TextView(applicationContext);
                    tv1.setTextColor(Color.parseColor("#bdbdbd"))
                    tv2.setTextColor(Color.parseColor("#bdbdbd"));
                    tv3.setTextColor(Color.parseColor("#bdbdbd"));
                    tv1.setPadding(30, 30, 30, 30);
                    tv2.setPadding(30, 30, 30, 30);
                    tv3.setPadding(30, 30, 30, 30);
                    tv1.text = data.getString(0);
                    tv2.text = data.getString(1);
                    tv3.text = data.getString(2);
                    tr.addView(tv1);
                    tr.addView(tv2);
                    tr.addView(tv3);
                    tr.setOnClickListener() {
                        id.setText(tv1.text);
                        name.setText(tv2.text);
                        age.setText(tv3.text);
                    }
                    tbl.addView(tr);
                }

            }
        }

        upd.setOnClickListener() {
            val id: String = id.text.toString()
            val name: String = name.text.toString()
            val age: String = age.text.toString()
            if (id == "" && name == "" && age == "") {
                Toast.makeText(this, "Data is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val data = sqLite.updateData(id, name, age)
                if (!data) {
                    Toast.makeText(
                        this, "Something is wrong in Database.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Record Updated Successfully.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        del.setOnClickListener() {
            val id: String = id.text.toString()
            if (id == "") {
                Toast.makeText(this, "Data is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val data = sqLite.delData(id)
                if (!data) {
                    Toast.makeText(
                        this, "Something is wrong in Database.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Record Deleted Successfully.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        clr.setOnClickListener() {
            id.text.clear()
            name.text.clear()
            age.text.clear()
            id.requestFocus()
        }

    }
}