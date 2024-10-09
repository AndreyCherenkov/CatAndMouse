package ru.andreycherenkov.catandmouse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TotalClicksDao(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mouse_game.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "statistics"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TOTAL_CLICKS = "total_clicks"
        private const val COLUMN_MOUSE_CLICKS = "mouse_clicks"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TOTAL_CLICKS INTEGER, $COLUMN_MOUSE_CLICKS INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertStatistics(totalClicks: Int, mouseClicks: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOTAL_CLICKS, totalClicks)
            put(COLUMN_MOUSE_CLICKS, mouseClicks)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}