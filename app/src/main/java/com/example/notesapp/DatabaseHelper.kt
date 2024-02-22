package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {



    companion object{
        private const val DATABASE_NAME="dbnotes"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="allnotes"
        private const val  COL_ID="id"
        private const val COL_TITLE="title"
        private const val COL_NOTE="note"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY,$COL_TITLE TEXT,$COL_NOTE TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, ov: Int, nv: Int) {

        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }
    fun insertN(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE,note.title)
            put(COL_NOTE,note.notedes)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }
    fun getallNote() :List<Note>{
        val notelist = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE))
            val notedes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE))
            val note = Note(id, title, notedes)
            notelist.add(note)
        }
        cursor.close()
        db.close()
        return notelist
    }
    fun updateNote(note: Note){
        val db= writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE,note.title)
            put(COL_NOTE,note.notedes)
        }
        val whereCl = "$COL_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereCl,whereArgs)
        db.close()
    }
    fun getNotebyId(notid :Int) :Note{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_ID = $notid"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE))
        val notedes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE))
        cursor.close()
        db.close()
        return Note(id, title, notedes)

    }
    fun deleteNote(notid :Int){

        val db= writableDatabase
        val whereCl = "$COL_ID = ?"
        val whereArgs = arrayOf(notid.toString())
        db.delete(TABLE_NAME,whereCl,whereArgs)
        db.close()
    }

}