package com.example.demo.content_provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(
  /* context = */ context,
  /* name = */ "students.db",
  /* factory = */ null,
  /* version = */ 1
) {
  override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL(
      """CREATE TABLE $TABLE_NAME (
        |$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        |$COLUMN_NAME TEXT NOT NULL)""".trimMargin()
    )
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    onCreate(db)
  }

  companion object {
    const val TABLE_NAME = "students"
    const val COLUMN_ID = "_id"
    const val COLUMN_NAME = "name"
  }

}

class StudentsProvider : ContentProvider() {
  private lateinit var dbHelper: StudentDatabaseHelper

  override fun onCreate(): Boolean {
    dbHelper = StudentDatabaseHelper(context!!)
    return true
  }

  override fun query(
    uri: Uri,
    projection: Array<out String>?,
    selection: String?,
    selectionArgs: Array<out String>?,
    sortOrder: String?
  ): Cursor? {
    val db = dbHelper.readableDatabase

    val cursor = db.query(
      /* table = */ StudentDatabaseHelper.TABLE_NAME,
      /* columns = */ projection,
      /* selection = */ selection,
      /* selectionArgs = */ selectionArgs,
      /* groupBy = */ null,
      /* having = */ null,
      /* orderBy = */ sortOrder
    )

    // Register to watch a content URI for changes
    cursor.setNotificationUri(context?.contentResolver, uri)

    return cursor
  }

  override fun getType(uri: Uri) = "vnd.android.cursor.dir/$AUTHORITY.${StudentDatabaseHelper.TABLE_NAME}"

  override fun insert(uri: Uri, values: ContentValues?): Uri? {

    val db = dbHelper.writableDatabase

    val rowId = db.insert(
      /* table = */ StudentDatabaseHelper.TABLE_NAME,
      /* nullColumnHack = */ null,
      /* values = */ values
    )

    // Notify registered observers that a row was updated and attempt to sync changes to the network.
    context?.contentResolver?.notifyChange(uri, null)

    return ContentUris.withAppendedId(CONTENT_URI, rowId)
  }

  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

  override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

  companion object {
    const val AUTHORITY = "com.example.demo.provider"
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/${StudentDatabaseHelper.TABLE_NAME}")
  }
}