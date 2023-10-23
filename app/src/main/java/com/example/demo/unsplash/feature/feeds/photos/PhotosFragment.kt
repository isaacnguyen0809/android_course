package com.example.demo.unsplash.feature.feeds.photos

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.databinding.FragmentPhotosBinding
import com.example.demo.unsplash.UnsplashServiceLocator
import com.example.demo.unsplash.core.BaseFragment

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

  private val photoUiItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
    PhotoUiItemAdapter(
      requestManager = Glide.with(this)
    )
  }

  private val viewModel by viewModels<PhotoViewModel>(
    factoryProducer = {
      viewModelFactory {
        addInitializer(PhotoViewModel::class) {
          PhotoViewModel(
            unsplashApiService = UnsplashServiceLocator.unsplashApiService
          )
        }
      }
    }
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvPhoto.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
      adapter = photoUiItemAdapter
    }

    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.getPhotos()
    }

    val linearLayoutManager = binding.rvPhoto.layoutManager as LinearLayoutManager
    binding.rvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount) {
          viewModel.loadNextPage()
        }
      }
    })

    viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
      binding.swipeRefreshLayout.isVisible = state is PhotoUiState.Success
      when (state) {
        is PhotoUiState.Success -> {
          photoUiItemAdapter.submitList(state.listPhoto)
          binding.run {
            progressBarEnd.isVisible = state.isLoadingNextPage
            progressBar.isVisible = false
          }

          binding.swipeRefreshLayout.run {
            if (state.isRefreshing) {
              post {
                if (!isRefreshing) {
                  isRefreshing = true
                }
              }
            } else {
              // hide indicator
              isRefreshing = false
            }
          }
        }

        is PhotoUiState.Failure -> {
          binding.progressBar.isVisible = false
          binding.progressBarEnd.isVisible = false
          photoUiItemAdapter.submitList(emptyList())
        }

        PhotoUiState.Loading -> {
          binding.progressBar.isVisible = true
          binding.progressBarEnd.isVisible = false
          photoUiItemAdapter.submitList(emptyList())
        }
      }
    }
  }

  companion object {
    fun newInstance() = PhotosFragment()
    private const val VISIBLE_THRESHOLD = 1
  }
}
