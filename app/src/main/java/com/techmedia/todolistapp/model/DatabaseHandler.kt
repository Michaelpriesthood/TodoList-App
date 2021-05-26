package com.techmedia.todolistapp.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoList Database"

        private const val TODO_TABLE = "TodoList"

        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_ISCHECKED = "isChecked"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_TASK_TABLE = ("CREATE TABLE " + TODO_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_ISCHECKED + " INTEGER " + ")")
        db?.execSQL(CREATE_TASK_TABLE)

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE")
        onCreate(db)
    }

    /**
     * Function to insert data
     */
    fun addTodoItem(todo: TodoModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, todo.title) // TodoModelClass title
        contentValues.put(KEY_ISCHECKED, todo.isChecked) // TodoModelClass isChecked

        // Inserting todo details using insert query.
        val success = db.insert(TODO_TABLE, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    //Method to read the records from database in form of ArrayList
    @SuppressLint("Recycle")
    fun viewTodoItem(): ArrayList<TodoModel> {

        val todoList: ArrayList<TodoModel> = ArrayList()

        // Query to select all the todos from the table.
        val selectQuery = "SELECT  * FROM $TODO_TABLE"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))

                val todo = TodoModel(id = id, title = title)
                todoList.add(todo)

            } while (cursor.moveToNext())
        }
        return todoList
    }

    /**
     * Function to update record
     */
    fun updateTodo(todo: TodoModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, todo.title) // TodoModelClass title
        contentValues.put(KEY_ISCHECKED, todo.isChecked) // TodoModelClass isChecked

        // Updating Row
        val success = db.update(TODO_TABLE, contentValues, KEY_ID + "=" + todo.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }


    /**
     * Function to delete record
     */
    fun deleteTodo(todo: TodoModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, todo.id) // TodoModelClass id
        // Deleting Row
        val success = db.delete(TODO_TABLE, KEY_ID + "=" + todo.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success


    }
}