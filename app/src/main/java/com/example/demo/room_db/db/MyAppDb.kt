package com.example.demo.room_db.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.room_db.daos.NoteDao
import com.example.demo.room_db.daos.UserDao
import com.example.demo.room_db.entities.NoteEntity
import com.example.demo.room_db.entities.UserEntity

@Database(
  entities = [
    UserEntity::class,
    NoteEntity::class,
  ],
  version = 1,
  exportSchema = false
)
abstract class MyAppDb : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun noteDao(): NoteDao

  companion object {
    private const val DB_NAME = "lecture7.db"

    // Double-checked locking singleton pattern

    @Volatile
    private var INSTANCE: MyAppDb? = null

    fun getInstance(
      context: Context
    ): MyAppDb {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          MyAppDb::class.java,
          DB_NAME
        ).build()
          .also { INSTANCE = it }
      }
    }
  }
}