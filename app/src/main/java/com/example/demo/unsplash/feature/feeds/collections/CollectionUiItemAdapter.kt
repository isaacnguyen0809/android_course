package com.example.demo.unsplash.feature.feeds.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.demo.databinding.CollectionUiItemLayoutBinding

object CollectionUiItemDiffUtilItemCallback : DiffUtil.ItemCallback<CollectionUIItem>() {
  override fun areItemsTheSame(oldItem: CollectionUIItem, newItem: CollectionUIItem) =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: CollectionUIItem, newItem: CollectionUIItem) =
    oldItem == newItem
}

class CollectionUiItemAdapter(
  private val requestManager: RequestManager,
) :
  ListAdapter<CollectionUIItem, CollectionUiItemAdapter.VH>(CollectionUiItemDiffUtilItemCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
    CollectionUiItemLayoutBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
  )

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.bind(getItem(position))
  }

  inner class VH(
    private val binding: CollectionUiItemLayoutBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CollectionUIItem) {
      binding.run {
        textTitle.text = item.title
        textDescription.text = item.description

        requestManager
          .load(item.coverPhotoUrl)
          .fitCenter()
          .centerCrop()
          .transition(withCrossFade())
          .into(imageView)
      }
    }
  }
}
