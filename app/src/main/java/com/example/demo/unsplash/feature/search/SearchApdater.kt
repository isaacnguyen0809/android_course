package com.example.demo.unsplash.feature.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.unsplash.feature.search.photos.SearchPhotosFragment
import com.example.demo.unsplash.feature.search.users.SearchUsersFragment

class SearchViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when(position) {
      PHOTO_SEARCH -> SearchPhotosFragment.newInstance()
      USER_SEARCH -> SearchUsersFragment.newInstance()
      else -> throw IllegalStateException("Invalid position: $position")
    }
  }

  companion object {
    const val PHOTO_SEARCH = 0
    const val USER_SEARCH = 1
  }

}
