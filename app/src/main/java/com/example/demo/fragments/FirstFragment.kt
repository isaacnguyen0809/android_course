package com.example.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.demo.R
import com.example.demo.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment() {
  private var _binding: FragmentFirstBinding? = null

  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    log("onCreateView")
    _binding = FragmentFirstBinding.inflate(
      inflater,
      container,
      false
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.button.setOnClickListener {
      parentFragmentManager.commit {
        replace<SecondFragment>(
          containerViewId = R.id.fragment_container,
          tag = "SecondFragment",
          args = bundleOf(
            "count" to 0,
            "name" to "hoc"
          )
        )
        setReorderingAllowed(true)

        addToBackStack("FirstFragment_to_SecondFragment")
      }
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}