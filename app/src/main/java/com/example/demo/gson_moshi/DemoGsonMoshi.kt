package com.example.demo.gson_moshi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.demo.databinding.ActivityDemoGsonMoshiBinding
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import retrofit2.create
import kotlin.LazyThreadSafetyMode.NONE

fun Context.readAssetAsText(fileName: String): String = assets.open(fileName)
  .bufferedReader()
  .use { it.readText() }

fun buildMoshi(): Moshi {
  return Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
}

fun buildGson(): Gson = Gson()

class DemoGsonMoshi : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityDemoGsonMoshiBinding.inflate(layoutInflater) }

  private val moshi by lazy { buildMoshi() }
  private val gson by lazy { buildGson() }

  private val jsonPlaceholderApiService: JsonPlaceholderApiService by lazy {
    buildRetrofit(
      baseUrl = "https://jsonplaceholder.typicode.com/",
      okHttpClient = buildOkHttpClient(),
      moshi = moshi
    ).create()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    Log.d(TAG, readAssetAsText("student.json"))
    Log.d(TAG, readAssetAsText("students.json"))

    binding.myButton.setOnClickListener { onClickButton() }
  }

  @OptIn(ExperimentalStdlibApi::class)
  private fun onClickButton() {
    //    lifecycleScope.launch {
    //      val student = withContext(Dispatchers.IO) {
    //        // TODO: Parse json in background thread
    //        val studentJsonString = readAssetAsText("student.json")
    //        val studentAdapter = moshi.adapter<Student>()
    //        studentAdapter.fromJson(studentJsonString)
    //      }
    //
    //      // TODO: Update UI on main thread
    //      binding.textView.text = "Parsed: $student"
    //    }

    //    lifecycleScope.launch {
    //      val studentList: List<Student>? = withContext(Dispatchers.IO) {
    //        // TODO: Parse json in background thread
    //        val studentListJsonString = readAssetAsText("students.json")
    //        val studentListAdapter = moshi.adapter<List<Student>>()
    //        studentListAdapter.fromJson(studentListJsonString)
    //      }
    //
    //      // TODO: Update UI on main thread
    //      binding.textView.text = studentList?.joinToString(separator = "\n") ?: "Null"
    //    }

    //    lifecycleScope.launch {
    //      val student = withContext(Dispatchers.IO) {
    //        // TODO: Parse json in background thread
    //        val studentJsonString = readAssetAsText("student.json")
    //        gson.fromJson<Student>(studentJsonString, Student::class.java)
    //      }
    //
    //      // TODO: Update UI on main thread
    //      binding.textView.text = "Parsed: $student"
    //    }

    //    lifecycleScope.launch {
    //      val studentList = withContext(Dispatchers.IO) {
    //        // TODO: Parse json in background thread
    //        val studentListJsonString = readAssetAsText("students.json")
    //
    //        gson.fromJson<List<Student>>(
    //          studentListJsonString,
    //          object : TypeToken<List<Student>>() {}.type,
    //        )
    //      }
    //
    //      // TODO: Update UI on main thread
    //      binding.textView.text = studentList?.joinToString(separator = "\n") ?: "Null"

    lifecycleScope.launch {
      binding.textView.text = "Loading..."
      try {
        val todos = jsonPlaceholderApiService.getTodos()
        binding.textView.text = "OK: ${todos.joinToString(separator = "\n") { it.title }}"
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        e.printStackTrace()
        binding.textView.text = "Failure: ${e.message}"
      }
    }
  }

  private companion object {
    private const val TAG = "DemoGsonMoshi"
  }
}