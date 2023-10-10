package com.example.demo.unsplash.feature.search.photos

import android.os.Bundle
import android.view.View
import com.example.demo.databinding.FragmentSearchPhotoBinding
import com.example.demo.unsplash.core.BaseFragment

class SearchPhotosFragment : BaseFragment<FragmentSearchPhotoBinding>(FragmentSearchPhotoBinding::inflate) {


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {
    fun newInstance() = SearchPhotosFragment()
  }
}
