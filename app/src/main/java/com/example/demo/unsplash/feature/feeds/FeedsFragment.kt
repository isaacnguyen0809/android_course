package com.example.demo.unsplash.feature.feeds

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.R
import com.example.demo.databinding.FragmentFeedsBinding
import com.example.demo.unsplash.core.BaseFragment
import com.example.demo.unsplash.feature.feeds.collections.CollectionsFragment
import com.example.demo.unsplash.feature.feeds.photos.PhotosFragment
import com.example.demo.unsplash.feature.search.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FeedsFragment : BaseFragment<FragmentFeedsBinding>(FragmentFeedsBinding::inflate) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonSearch.setOnClickListener {
      parentFragmentManager.commit {
        setReorderingAllowed(true)

        addToBackStack("search")

        replace<SearchFragment>(
          containerViewId = R.id.container_view,
          tag = SearchFragment::class.java.simpleName,
        )
      }
    }

    setupViewPager()
  }

  private fun setupViewPager() {
    binding.viewPager.run {
      adapter = FeedsViewPagerAdapter(this@FeedsFragment)

      TabLayoutMediator(
        binding.tabsLayout,
        this
      ) { tab, position ->
        tab.text = when(position) {
          0 -> "Collections"
          1 -> "Photos"
          else -> throw IllegalStateException("Invalid position: $position")
        }
      }.attach()
    }
  }
}

class FeedsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when(position) {
      0 -> CollectionsFragment.newInstance()
      1 -> PhotosFragment.newInstance()
      else -> throw IllegalStateException("Invalid position: $position")
    }
  }

}
