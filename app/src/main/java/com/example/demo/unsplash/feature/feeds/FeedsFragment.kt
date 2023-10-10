package com.example.demo.unsplash.feature.feeds

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.demo.R
import com.example.demo.databinding.FragmentFeedsBinding
import com.example.demo.unsplash.core.BaseFragment
import com.example.demo.unsplash.feature.search.SearchFragment

class FeedsFragment : BaseFragment<FragmentFeedsBinding>(FragmentFeedsBinding::inflate) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonSearch.setOnClickListener {
      parentFragmentManager.commit {
        setReorderingAllowed(true)

        addToBackStack("search")

        replace<SearchFragment>(
          containerViewId = R.id.fragment_container,
          tag = SearchFragment::class.java.simpleName,
        )
      }
    }
  }
}
