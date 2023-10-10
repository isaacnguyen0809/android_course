package com.example.demo.unsplash.feature.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.demo.databinding.FragmentSearchBinding
import com.example.demo.unsplash.UnsplashServiceLocator
import com.example.demo.unsplash.core.BaseFragment
import com.example.demo.unsplash.feature.search.SearchViewPagerAdapter.Companion.PHOTO_SEARCH
import com.example.demo.unsplash.feature.search.SearchViewPagerAdapter.Companion.USER_SEARCH
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

  private val viewModel by activityViewModels<SearchViewModel>(
    factoryProducer = {
      viewModelFactory {
        addInitializer(SearchViewModel::class) {
          SearchViewModel(unsplashApiService = UnsplashServiceLocator.unsplashApiService)
        }
      }
    }
  )

  private val tabIndexs by lazy {
    mapOf(
      PHOTO_SEARCH to "Photos",
      USER_SEARCH to "User",
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.toolbar.setNavigationOnClickListener {
      parentFragmentManager.popBackStack()
    }
    setupSearchAdapter()

    binding.searchEditText.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable?) {
         viewModel.searchPhotos(s.toString())
      }
    })
  }

  private fun setupSearchAdapter() = binding.apply {
    val adapter = SearchViewPagerAdapter(this@SearchFragment)
    viewPager.adapter = adapter
    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
      tab.text = tabIndexs[position]
    }.attach()
  }
}
