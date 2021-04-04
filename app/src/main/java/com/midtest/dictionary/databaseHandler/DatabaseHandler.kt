package com.midtest.dictionary.databaseHandler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Database_Translate"
        private val TABLE_NAME = "Translate_History"
        private val key_id = "_id"
        private val key_bahasa = "bahasa"
        private val key_kalimat = "kalimat"
        private val key_bahasatujuan = "bahasatujuan"
        private val key_kalimathasil = "kalimathasil"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createtabel = ("CREATE TABLE " + TABLE_NAME + "(" +
                key_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                key_bahasa+ " TEXT,"+
                key_kalimat+ " TEXT,"+
                key_bahasatujuan+ " TEXT,"+
                key_kalimathasil+ " TEXT)"
                )
        db?.execSQL(createtabel)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME)
        onCreate(db)
    }

    fun addData(bahasa: String,kalimat: String,bahasatujuan: String,kalimathasil: String): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(key_bahasa,bahasa)
        cv.put(key_kalimat,kalimat)
        cv.put(key_bahasatujuan,bahasatujuan)
        cv.put(key_kalimathasil,kalimathasil)
        val success = db.insert(TABLE_NAME,null,cv)
        db.close()
        return success
    }

//    fun getAll():ArrayList<History>{
//        val empTranslate: ArrayList<History> = ArrayList<History>()
//        val query = "SELECT * FROM $TABLE_NAME"
//        val db = this.readableDatabase
//        var cursor: Cursor? = null
//        try {
//            cursor = db.rawQuery(query, null)
//        }catch (e: SQLiteException){
//            db.execSQL(query)
//            return ArrayList()
//        }
//        if(cursor.moveToFirst()){
//            do{
//                val translateId: Int = cursor.getInt(cursor.getColumnIndex("_id"))
//                val translateBahasa: String = cursor.getString(cursor.getColumnIndex("bahasa"))
//                val translateKalimat: String = cursor.getString(cursor.getColumnIndex("kalimat"))
//                val translateBahasaTujuan: String = cursor.getString(cursor.getColumnIndex("bahasatujuan"))
//                val translateKalimatHasil: String = cursor.getString(cursor.getColumnIndex("kalimathasil"))
//                val tempTranslate = History(
//                        id=translateId,
//                        bahasa=translateBahasa,kalimat=translateKalimat,
//                        bahasatujuan=translateBahasaTujuan,kalimathasil=translateKalimatHasil)
//                empTranslate.add(tempTranslate)
//            }while (cursor.moveToNext())
//        }
//        return empTranslate
//    }

    fun delete(TranslateId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(key_id, TranslateId)
        val success = db.delete(TABLE_NAME, "_id="+TranslateId, null)
        db.close()
        return success
    }
}