package com.example.healthandfitness

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context,factory: SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION)
{
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Exercise.db"
        private val TABLE_NAME = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_HISTORY_TABLE = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_COMPLETED_DATE TEXT)"
        db?.execSQL(CREATE_HISTORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addDate(date:String)
    {
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE,date)
        val db = this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getDates():ArrayList<String>
    {
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)

        while (cursor.moveToNext()) {
            val dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(dateValue)
        }
        cursor.close()
        return list
    }

}