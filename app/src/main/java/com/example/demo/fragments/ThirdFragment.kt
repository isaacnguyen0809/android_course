package com.example.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.demo.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment() {
  private var _binding: FragmentThirdBinding? = null

  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    log("onCreateView")
    _binding = FragmentThirdBinding.inflate(
      inflater,
      container,
      false
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonBack.setOnClickListener {
      parentFragmentManager.popBackStack()
    }

    binding.buttonBackToFirst.setOnClickListener {
      parentFragmentManager.popBackStack(
        "FirstFragment_to_SecondFragment",
        FragmentManager.POP_BACK_STACK_INCLUSIVE
      )
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}