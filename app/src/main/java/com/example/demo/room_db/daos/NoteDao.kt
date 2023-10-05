package com.example.demo.room_db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.demo.room_db.entities.NoteEntity
import com.example.demo.room_db.entities.UserAndNotes

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes")
  fun observeAll(): LiveData<List<NoteEntity>>

  @Query("SELECT * FROM notes WHERE user_id = :userId ORDER BY id DESC LIMIT 1")
  suspend fun getLastByUserId(userId: Int): NoteEntity?

  @Query("SELECT * FROM notes WHERE id = :noteId")
  fun observeById(noteId: Int): LiveData<NoteEntity?>

  @Insert
  suspend fun insertMany(notes: List<NoteEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: NoteEntity)

  @Update
  suspend fun update(note: NoteEntity)

  @Delete
  suspend fun delete(note: NoteEntity)

  @Query("SELECT * FROM users WHERE id = :userId")
  @Transaction
  fun observeUserAndNotesByUserId(userId: Int): LiveData<UserAndNotes?>
}