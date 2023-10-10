package com.example.demo.unsplash.feature.feeds.photos

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.demo.databinding.FragmentPhotosBinding
import com.example.demo.unsplash.core.BaseFragment

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  companion object {
    fun newInstance() = PhotosFragment()
  }
}
