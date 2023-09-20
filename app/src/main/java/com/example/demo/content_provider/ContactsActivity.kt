package com.example.demo.content_provider

import android.Manifest
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.core.database.getStringOrNull
import com.example.demo.databinding.ActivityContactsBinding
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.random.Random

class ContactsActivity : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityContactsBinding.inflate(layoutInflater) }

  private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
    if (granted) {
      queryContacts()
    } else {
      Toast.makeText(this, "Denied READ_CONTACTS permission", Toast.LENGTH_SHORT).show()
    }
  }

  //region Students provider
  private val studentsUri: Uri = Uri.parse("content://com.example.demo.provider/students")
  private val studentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
    override fun onChange(selfChange: Boolean) {
      super.onChange(selfChange)
      queryStudentsProvider()
    }
  }
  //endregion

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // make TextViews scrollable
    binding.textView.movementMethod = ScrollingMovementMethod()
    binding.textView2.movementMethod = ScrollingMovementMethod()

    if (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_CONTACTS
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      queryContacts()
    } else {
      permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    binding.buttonInsertStudent.setOnClickListener { insertNewStudent() }

    // Observe changes of students provider
    contentResolver.registerContentObserver(
      studentsUri,
      false,
      studentObserver,
    )

    queryStudentsProvider()
  }

  override fun onDestroy() {
    contentResolver.unregisterContentObserver(studentObserver)

    super.onDestroy()
  }

  private fun queryContacts() {
    val cursor = contentResolver.query(
      /* uri = */ ContactsContract.Contacts.CONTENT_URI,
      /* projection = */ arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
      /* selection = */ "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?",
      /* selectionArgs = */ arrayOf("B%"),
      /* sortOrder = */ "${ContactsContract.Contacts.DISPLAY_NAME} ASC",
    )

    cursor?.use {
      val s = StringBuilder()

      if (it.count > 0) {
        val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
        val displayNameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        var count = 0

        while (it.moveToNext()) {
          val id = it.getStringOrNull(index = idIndex)
          val name = it.getStringOrNull(index = displayNameIndex)

          s.append("$count: { id: $id, name: $name}\n")

          count++
        }
      } else {
        s.append("Empty list")
      }

      if (isDestroyed) {
        return
      }
      binding.textView.text = s
    }
  }

  //region Students provider
  private fun insertNewStudent() {
    contentResolver
      .insert(
        studentsUri,
        contentValuesOf(
          "name" to "Hoc${Random.nextInt()}"
        ),
      )
      .let { Log.d(TAG, "insert: result=$it") }
  }

  private fun queryStudentsProvider() {
    contentResolver
      .query(
        studentsUri,
        arrayOf("_id", "name"),
        null,
        null,
        "name ASC"
      )
      ?.use { cursor ->
        val students = cursor.toList {
          val id = it.getStringOrNull(index = it.getColumnIndex("_id"))
          val name = it.getStringOrNull(index = it.getColumnIndex("name"))
          id to name
        }
        Log.d("ContactsActivity", "query: $students")

        binding.textView2.text = students.joinToString(separator = "\n")

        cursor.registerContentObserver(studentObserver)
      }
  }
  //endregion

  private companion object {
    const val TAG = "ContactsActivity"
  }
}

fun <T> Cursor.toList(mapper: (Cursor) -> T): List<T> = buildList<T> {
  while (moveToNext()) {
    add(mapper(this@toList))
  }
}
