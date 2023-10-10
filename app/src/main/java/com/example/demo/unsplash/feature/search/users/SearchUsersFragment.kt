package com.example.demo.unsplash.feature.search.users

import com.example.demo.databinding.FragmentSearchUserBinding
import com.example.demo.unsplash.core.BaseFragment

class SearchUsersFragment : BaseFragment<FragmentSearchUserBinding>(FragmentSearchUserBinding::inflate) {

  override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {
    fun newInstance() = SearchUsersFragment()
  }
}
