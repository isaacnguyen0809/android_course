package com.example.demo.shared_prefs

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.demo.databinding.ActivityDemoSharedPrefsBinding
import kotlin.LazyThreadSafetyMode.NONE

class DemoSharedPrefsViewModel(
  application: Application
) : AndroidViewModel(application) {
  private val _darkThemeLiveData = MutableLiveData(false)
  val darkThemeLiveData: LiveData<Boolean> get() = _darkThemeLiveData

  val name: String get() = sharedPrefs.getString(NAME_KEY, "").orEmpty()

  private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
  private val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
    if (key == DARK_THEME_KEY) {
      _darkThemeLiveData.value = prefs.getBoolean(DARK_THEME_KEY, false)
    }
  }

  init {
    _darkThemeLiveData.value = sharedPrefs.getBoolean(DARK_THEME_KEY, false)
    sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
  }

  fun toggleTheme() {
    val current = sharedPrefs.getBoolean(DARK_THEME_KEY, false)

    Log.d(DemoSharedPrefs.TAG, "switchToggleTheme: isDarkTheme=$current")

    //      sharedPrefs.edit()
    //        .putBoolean(DARK_THEME_KEY, !current)
    //        .apply()

    sharedPrefs.edit(commit = false) {
      putBoolean(DARK_THEME_KEY, !current)
    }
  }

  fun saveName(name: String) {
    sharedPrefs.edit(commit = false) {
      putString(NAME_KEY, name)
    }
  }

  override fun onCleared() {
    super.onCleared()
    sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
  }

  private companion object {
    private const val DARK_THEME_KEY = "dark_theme"
    private const val NAME_KEY = "name"
  }
}

class DemoSharedPrefs : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityDemoSharedPrefsBinding.inflate(layoutInflater) }
  private val vm by viewModels<DemoSharedPrefsViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    binding.switchToggleTheme.setOnClickListener {
      vm.toggleTheme()
    }
    vm.darkThemeLiveData.observe(this) {
      AppCompatDelegate.setDefaultNightMode(
        if (it) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
      )
    }

    binding.buttonSave.setOnClickListener {
      vm.saveName(binding.textInputName.editText!!.text.toString())
    }
  }

  override fun onResume() {
    super.onResume()
    binding.switchToggleTheme.isChecked = vm.darkThemeLiveData.value!!
    binding.textInputName.editText!!.setText(vm.name)
  }

  companion object {
    const val TAG = "DemoSharedPrefs"
  }
}