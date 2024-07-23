package com.pmgohil.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.android.material.internal.NavigationMenu
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "data"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NM = "data"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createDataTable =
            ("CREATE TABLE " + TABLE_NM + "(" + ID + "INTEGER PRIMARY KEY" + NAME + "TEXT" + EMAIL + "TEXT" + ")")
        db?.execSQL(createDataTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NM")
        onCreate(db)
    }

    fun insertData(d: DataModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, d.id)
        contentValues.put(NAME, d.name)
        contentValues.put(EMAIL, d.email)

        val success = db.insert(TABLE_NM, null, contentValues)
        db.close()
        return success
    }
    //https://www.youtube.com/watch?v=9LYn-OBO5qE
}