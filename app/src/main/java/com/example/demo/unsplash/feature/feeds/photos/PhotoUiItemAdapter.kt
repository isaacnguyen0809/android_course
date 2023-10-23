package com.example.demo.unsplash.feature.feeds.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.demo.databinding.ItemPhotoBinding

object PhotoUiItemDiffCallback : DiffUtil.ItemCallback<PhotoUiItem>() {
  override fun areItemsTheSame(oldItem: PhotoUiItem, newItem: PhotoUiItem): Boolean =
    oldItem.id == newItem.id


  override fun areContentsTheSame(oldItem: PhotoUiItem, newItem: PhotoUiItem): Boolean =
    oldItem == newItem
}

class PhotoUiItemAdapter(
  private val requestManager: RequestManager
) : ListAdapter<PhotoUiItem, PhotoUiItemAdapter.ViewHolder>(PhotoUiItemDiffCallback) {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    ItemPhotoBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: ItemPhotoBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PhotoUiItem) {
      binding.run {
        tvAuthorName.text = item.username
        item.des.isNullOrEmpty().apply {
          tvDes.isVisible = this
          if (!this) tvDes.text = item.des
        }
        tvNumberLikes.text = item.likes.toString()

        requestManager
          .load(item.imageContent)
          .fitCenter()
          .centerCrop()
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(ivImage)

        requestManager
          .load(item.imageProfile)
          .fitCenter()
          .centerCrop()
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(ivAvatar)
      }
    }
  }
}
