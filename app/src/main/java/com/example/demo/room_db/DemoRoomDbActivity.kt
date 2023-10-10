package com.example.demo.room_db

import android.app.Application
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.demo.databinding.ActivityDemoRoomDbBinding
import com.example.demo.room_db.db.MyAppDb
import com.example.demo.room_db.entities.NoteEntity
import com.example.demo.room_db.entities.UserAndNotes
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.random.Random
import kotlinx.coroutines.launch

class DemoRoomDbViewModel(application: Application) : AndroidViewModel(application) {
  val userAndNotesOf1LiveData: LiveData<UserAndNotes?> =
    MyAppDb.getInstance(application)
      .noteDao()
      .observeUserAndNotesByUserId(userId = 1)

//  init {
//    viewModelScope.launch {
//      MyAppDb.getInstance(application)
//        .userDao()
//        .insert(
//          UserEntity(
//            id = 1,
//            name = "USER 1####",
//          )
//        )
//    }
//  }

  fun delete() {
    viewModelScope.launch {
      val db = MyAppDb.getInstance(getApplication())

      db.withTransaction {
        db.noteDao()
          .getLastByUserId(userId = 1)
          ?.let {
            db.noteDao().delete(it)
          }
      }

      Toast.makeText(getApplication(), "Removed", Toast.LENGTH_SHORT).show()
    }
  }

  fun addNewData() {
    viewModelScope.launch {
      val db = MyAppDb.getInstance(getApplication())

      db.withTransaction {
        val notes = List(10) { index ->
          val id = Random.nextInt()
          NoteEntity(
            id = id,
            title = "title #$id",
            userId = 1
          )
        }

        db.noteDao().insertMany(notes)
      }

      Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show()
    }
  }
}

class DemoRoomDbActivity : AppCompatActivity() {
  private val binding by lazy(NONE) {
    ActivityDemoRoomDbBinding.inflate(layoutInflater)
  }
  private val vm by viewModels<DemoRoomDbViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    binding.buttonAdd.setOnClickListener {
      vm.addNewData()
    }
    binding.buttonDelete.setOnClickListener {
      vm.delete()
    }

    binding.textInfo.movementMethod = ScrollingMovementMethod()

    vm.userAndNotesOf1LiveData.observe(this) { userAndNotes ->
      binding.textInfo.text = """
      | User: ${userAndNotes?.user},
      | Count: ${userAndNotes?.notes?.size ?: 0}
      | Notes: ${userAndNotes?.notes?.joinToString(separator = "\n")}
      """.trimMargin()
    }
  }
}
