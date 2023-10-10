package com.example.demo.unsplash.feature.search.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.demo.databinding.FragmentSearchPhotoBinding
import com.example.demo.unsplash.UnsplashServiceLocator
import com.example.demo.unsplash.core.BaseFragment
import com.example.demo.unsplash.feature.feeds.collections.CollectionUiItemAdapter
import com.example.demo.unsplash.feature.search.SearchViewModel

class SearchPhotosFragment :
  BaseFragment<FragmentSearchPhotoBinding>(FragmentSearchPhotoBinding::inflate) {

  private val collectionUiItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CollectionUiItemAdapter(
      requestManager = Glide.with(this)
    )
  }

  private val viewModel by activityViewModels<SearchViewModel>(
    factoryProducer = {
      viewModelFactory {
        addInitializer(SearchViewModel::class) {
          SearchViewModel(unsplashApiService = UnsplashServiceLocator.unsplashApiService)
        }
      }
    }
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()

    viewModel.searchPhotoResult.observe(viewLifecycleOwner) {
      collectionUiItemAdapter.submitList(it)
    }

  }

  private fun setupViews() {
    binding.recyclerViewPhoto.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
      adapter = collectionUiItemAdapter
    }
  }

  companion object {
    fun newInstance() = SearchPhotosFragment()
  }
}
