package com.example.android_advance.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android_advance.model.response.UserDto

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "zola.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONENUMBER = "phoneNumber"
        private const val COLUMN_FRIENDS = "friends"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserQuery =
            "CREATE TABLE $TABLE_USER ($COLUMN_ID TEXT PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_PHONENUMBER TEXT)"
        db?.execSQL(createUserQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_USER"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun getUser(): List<UserDto> {
        val listUser = mutableListOf<UserDto>()
        val db = readableDatabase
        val tableQuery = "SELECT * FROM $TABLE_USER"
        val cursor = db.rawQuery(tableQuery, null);

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONENUMBER))
            val user = UserDto(id, name, phoneNumber)
            listUser.add(user)
        }
        cursor.close()
        db.close()
        return listUser

    }

    fun insertUser(userDto: UserDto) {
        val db = writableDatabase

        // Check if user already exists
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(userDto.id)
        val cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null)

        if (cursor.count == 0) {
            // User doesn't exist, insert it
            val values = ContentValues().apply {
                put(COLUMN_ID, userDto.id)
                put(COLUMN_NAME, userDto.name)
                put(COLUMN_PHONENUMBER, userDto.phoneNumber)
            }
            db.insert(TABLE_USER, null, values)
        }

        cursor.close()
        db.close()
    }

    fun deleteUser()
    {
        val db = writableDatabase
        db.delete(TABLE_USER, null, null)
        db.close()
    }
}