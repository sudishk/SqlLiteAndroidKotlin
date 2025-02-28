package com.edugaon.sqliteandroidkotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context):SQLiteOpenHelper(context, "edugaon.db", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
      p0?.execSQL("CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun  insertStudent(name: String, email: String): Long {
        val db = this.writableDatabase
        val student = ContentValues()
        student.put("name", name)
        student.put("email", email)
        val result = db.insert("students",null, student )
       return result
    }

    fun getAllStudents(): ArrayList<StudentModel> {
        val db = this.writableDatabase
        val cursorResult = db.rawQuery("SELECT * FROM students", null)
        val students = ArrayList<StudentModel>()
        if (cursorResult.moveToFirst()){
            while (!cursorResult.isAfterLast){
                val id = cursorResult.getInt(cursorResult.getColumnIndexOrThrow("id"))
                val name = cursorResult.getString(cursorResult.getColumnIndexOrThrow("name"))
                val email = cursorResult.getString(cursorResult.getColumnIndexOrThrow("email"))
                students.add(StudentModel(id,name,email))
                cursorResult.moveToNext()
            }

        }

        return students
    }

    fun updateStudent(id:Int, name: String, email: String): Int {
        val  db = this.writableDatabase
        val student = ContentValues()
        student.put("name", name)
        student.put("email", email)
        val result = db.update("students", student,"id=?", arrayOf("$id"))
        return result
    }

    fun deleteStudent(id:Int): Int {
        val  db = this.writableDatabase
        val result = db.delete("students","id=?", arrayOf("$id"))
        return result
    }

}