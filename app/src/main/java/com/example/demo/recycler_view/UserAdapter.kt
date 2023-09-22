package com.example.demo.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.UserItemLayoutBinding

object UserDiffUtilItemCallback : DiffUtil.ItemCallback<User>() {
  override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
    // compare by id, ...
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
    // equals (==)
    return oldItem == newItem
  }
}

// Not use MutableList -> X
// readonly-List  or ImmutableList
// each item of List -> data class, val (Immutable), eg. User
// map -> update items
// filter -> remove items
// plus(+) -> add items
class UserAdapter(
  private val onRemoveItem: (User) -> Unit,
) : ListAdapter<User, UserAdapter.VH>(UserDiffUtilItemCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val binding = UserItemLayoutBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return VH(binding)
  }

  override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

  inner class VH(private val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
      binding.imageRemove.setOnClickListener {
        val pos = adapterPosition
        if (pos != RecyclerView.NO_POSITION) {
          onRemoveItem(getItem(pos))
        }
      }
    }

    fun bind(user: User) {
      binding.run {
        textName.text = user.name
        textEmail.text = user.email
      }
    }
  }

}