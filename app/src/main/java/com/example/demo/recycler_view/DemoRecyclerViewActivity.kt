package com.example.demo.recycler_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ActivityDemoRecyclerViewBinding
import java.util.UUID
import kotlin.LazyThreadSafetyMode.NONE

class DemoRecyclerViewActivity : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityDemoRecyclerViewBinding.inflate(layoutInflater) }

  private val userAdapter by lazy(NONE) {
    UserAdapter(
      onRemoveItem = ::onRemoveItem
    )
  }

  private var users: List<User> = emptyList() // Immutable List

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    setupRecyclerView()

    users = List(10) { index ->
      User(
        id = UUID.randomUUID().toString(),
        name = "Name #$index",
        email = "email_$index@gmail.com"
      )
    }
    userAdapter.submitList(users)

    binding.floatingActionButton.setOnClickListener {
      users = listOf(
        User(
          id = UUID.randomUUID().toString(),
          name = "Name #${users.size}",
          email = "email_${users.size}@gmail.com"
        )
      ) + users

      userAdapter.submitList(users) {
        binding.recyclerview.scrollToPosition(0)
      }
    }
  }

  private fun onRemoveItem(user: User) {
    users = users.filter { it.id != user.id }
    userAdapter.submitList(users)
  }

  private fun setupRecyclerView() {
    binding.recyclerview.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      adapter = userAdapter
    }
  }
}