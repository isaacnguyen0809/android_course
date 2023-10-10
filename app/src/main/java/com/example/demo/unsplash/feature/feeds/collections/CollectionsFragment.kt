package com.example.demo.unsplash.feature.feeds.collections

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.databinding.FragmentCollectionsBinding
import com.example.demo.unsplash.UnsplashServiceLocator
import com.example.demo.unsplash.core.BaseFragment
import kotlin.LazyThreadSafetyMode.NONE

class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>(FragmentCollectionsBinding::inflate) {
  private val collectionUiItemAdapter by lazy(NONE) {
    CollectionUiItemAdapter(
      requestManager = Glide.with(this)
    )
  }

  private val viewModel by viewModels<CollectionsViewModel>(
    factoryProducer = {
      viewModelFactory {
        addInitializer(CollectionsViewModel::class) {
          CollectionsViewModel(
            unsplashApiService = UnsplashServiceLocator.unsplashApiService
          )
        }
      }
    }
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupViews()
    bindViewModel()
  }

  private fun bindViewModel() {
    val linearLayoutManager = binding.recyclerViewCollections.layoutManager as LinearLayoutManager
    binding.recyclerViewCollections.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (
          dy > 0 &&
          linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount
        ) {
          // ....
          viewModel.loadNextPage()
        }
      }
    })

    binding.swipeRefreshLayoutCollections.setOnRefreshListener {
      viewModel.refresh()
    }

    viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
      when (state) {
        is CollectionsUiState.FirstPageFailure -> {
          collectionUiItemAdapter.submitList(emptyList())

          binding.run {
            // show retry button, hide loading
            buttonRetryCollections.isVisible = true
            buttonRetryCollections.text = "First page failed, retry?"
            progressBarCollections.isVisible = false
          }
        }

        CollectionsUiState.FirstPageLoading -> {
          collectionUiItemAdapter.submitList(emptyList())

          binding.run {
            // hide retry button, show loading
            progressBarCollections.isVisible = true
            buttonRetryCollections.isVisible = false
          }
        }

        is CollectionsUiState.Content -> {
          collectionUiItemAdapter.submitList(state.items)

          binding.swipeRefreshLayoutCollections.run {
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

          binding.run {
            // show loading when loading next page
            progressBarCollections.isVisible = state.isLoadingNextPage

            // show retry button when has error loading next page
            buttonRetryCollections.isVisible = state.errorNextPage != null
            buttonRetryCollections.text = "Error loading next page, retry?"
          }
        }
      }
    }
  }

  override fun onDestroyView() {
    binding.recyclerViewCollections.adapter = null // avoid memory leak
    super.onDestroyView()
  }

  private fun setupViews() {
    binding.recyclerViewCollections.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
      adapter = collectionUiItemAdapter
    }
  }

  companion object {
    fun newInstance() = CollectionsFragment()

    private const val VISIBLE_THRESHOLD = 1
  }
}
