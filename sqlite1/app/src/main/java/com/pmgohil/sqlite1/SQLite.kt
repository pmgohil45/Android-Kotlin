package com.pmgohil.sqlite1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


internal class SQLite  //Context thisContext;
    (context: Context?) : SQLiteOpenHelper(context, "DBASE", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table emp(ID TEXT,NAME TEXT,AGE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("drop table if exists emp")
        onCreate(db)
    }

    fun insertData(ID: String?, Name: String?, age: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ID", ID)
        contentValues.put("NAME", Name)
        contentValues.put("age", age)
        val res = db.insert("emp", null, contentValues)
        return res != -1L
    }

    fun updateData(ID: String, Name: String?, age: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME", Name)
        contentValues.put("age", age)
        db.update("emp", contentValues, "ID=$ID", null)
        return true
    }

    fun delData(ID: String): Boolean {
        val db = this.writableDatabase
        db.delete("emp", "ID=$ID", null)
        return true
    }

    fun selData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from emp", null)
    }
}
