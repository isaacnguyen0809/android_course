package com.example.demo.content_provider

import android.Manifest
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.core.database.getStringOrNull
import com.example.demo.databinding.ActivityContactsBinding
import kotlin.LazyThreadSafetyMode.NONE

class ContactsActivity : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityContactsBinding.inflate(layoutInflater) }

  private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
    if (granted) {
      queryContacts()
    } else {
      Toast.makeText(this, "Denied READ_CONTACTS permission", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    if (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_CONTACTS
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      queryContacts()
    } else {
      permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    contentResolver
      .query(
        Uri.parse("content://com.example.demo.provider/students"),
        arrayOf("_id", "name"),
        null,
        null,
        "name ASC"
      )
      .use {
        it?.registerContentObserver(object : ContentObserver(Handler(Looper.getMainLooper())) {
          override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
            super.onChange(selfChange, uri, flags)
          }
        })
      }

    contentResolver
      .insert(
        Uri.parse("content://com.example.demo.provider/students"),
        contentValuesOf(
          "name" to "Hoc"
        ),
      )
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

      binding.textView.text = s
    }
  }
}