package com.example.demo.room_db.entities

import androidx.room.Embedded
import androidx.room.Relation

// 1-N
data class UserAndNotes(
  @Embedded
  val user: UserEntity,
  @Relation(
    parentColumn = "id",
    entityColumn = "user_id",
    entity = NoteEntity::class,
  )
  val notes: List<NoteEntity>,
)