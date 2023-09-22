package com.example.demo

import android.Manifest
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.demo.content_provider.ContactsActivity
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.fragments.DemoFragmentsActivity
import com.example.demo.recycler_view.DemoRecyclerViewActivity
import com.example.demo.service.DemoBoundService
import com.example.demo.service.MyForegroundService

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  private var counter: Int = 0


  //region Bound service
  private var bound = false
  private var demoBoundService: DemoBoundService? = null
  private val connection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
      bound = true
      demoBoundService = (service as DemoBoundService.LocalBinder).getDemoBoundService()

      Toast.makeText(
        this@MainActivity,
        "Connected: ${demoBoundService!!.getDemoData()}",
        Toast.LENGTH_SHORT
      ).show()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
      bound = false
      demoBoundService = null
    }

  }
  //endregion

  private val smsBroadCastReceiver = SmsBroadCastReceiver()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate: ")

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.tvClick.setOnClickListener {
      requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
    }

    if (savedInstanceState != null) {
      counter = savedInstanceState.getInt(KEY_COUNTER, 0)
      binding.tvCounter.text = counter.toString()
    }

    binding.tvCounter.setOnClickListener {
      val intent = Intent(this, SmsActivity::class.java)
      resultLauncher.launch(intent)
    }

    // Register broadcast receiver
    val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
    registerReceiver(smsBroadCastReceiver, intentFilter)

    demoForegroundService()
    demoContentProvider()
    demoFragments()
    demoRecyclerView()
  }


  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(KEY_COUNTER, counter)
  }

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
      if (result) {
        Toast.makeText(this, "Permission Success ", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
      }
    }
  private val resultLauncher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == RESULT_OK) {
        val intentData = result.data
        val message = intentData?.getStringExtra("result")
        //                binding.tvMain.text = message
        Toast.makeText(this, "Result: $message", Toast.LENGTH_SHORT).show()
      }
    }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart: ")

    this.bindService(
      Intent(this, DemoBoundService::class.java),
      connection,
      Service.BIND_AUTO_CREATE,
    )
  }

  override fun onRestart() {
    super.onRestart()
    Log.d(TAG, "onRestart: ")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume: ")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause: ")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop: ")

    if (bound) {
      unbindService(connection)
      bound = false
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(smsBroadCastReceiver)
    Log.d(TAG, "onDestroy: ")
  }

  private fun demoForegroundService() {
    binding.buttonStartForegroundService.setOnClickListener {
      ContextCompat.startForegroundService(
        this,
        Intent(this, MyForegroundService::class.java).apply {
          putExtra(MyForegroundService.ACTION_EXTRA_KEY, "START")
        }
      )
    }

    binding.buttonStopForegroundService.setOnClickListener {
      stopService(Intent(this, MyForegroundService::class.java))
    }
  }

  private fun demoContentProvider() {
    binding.buttonToContacts.setOnClickListener {
      startActivity(Intent(this, ContactsActivity::class.java))
    }
  }

  private fun demoFragments() {
    binding.buttonToFragments.setOnClickListener {
      startActivity(Intent(this, DemoFragmentsActivity::class.java))
    }
  }

  private fun demoRecyclerView() {
    binding.buttonToRecyclerView.setOnClickListener {
      startActivity(Intent(this, DemoRecyclerViewActivity::class.java))
    }
  }

  companion object {
    private const val TAG = "MainActivity"
    private const val KEY_COUNTER = "KEY_COUNTER"
  }
}