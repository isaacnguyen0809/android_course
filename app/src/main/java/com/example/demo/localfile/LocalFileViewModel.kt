package com.example.demo.localfile

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class LocalFileViewModel(private val application: Application) : AndroidViewModel(application) {

    private val internalPath = application.getExternalFilesDir(null as String?)
    private val temptFolderImage = "$internalPath/temptFolderImage"

    private val ANDROID_DIRECTORY = "Android_003"

    val ANDROID_RELATIVE_PATH =
        "${Environment.DIRECTORY_PICTURES}${File.separator}$ANDROID_DIRECTORY"

    val ANDROID_LEGACY_PATH =
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}${File.separator}$ANDROID_DIRECTORY"

    fun saveImageInternal(bitmap: Bitmap) {
        val tempDataFolder = temptFolderImage
        val folderData = File(tempDataFolder)
        if (!folderData.exists()) {
            folderData.mkdirs()
        }
        val outFile = File("$tempDataFolder/${System.currentTimeMillis()}.jpg")
        val fos = FileOutputStream(outFile)
        fos.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
    }

    fun saveImageExternal(bitmap: Bitmap) {
        val fileName = "${System.currentTimeMillis()}"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            application.applicationContext.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, ANDROID_RELATIVE_PATH)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val directory = File(ANDROID_LEGACY_PATH)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val image = File(ANDROID_LEGACY_PATH, fileName)
            MediaScannerConnection.scanFile(
                application.applicationContext, arrayOf(image.absolutePath),
                arrayOf("image/jpeg"), null
            )
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
}