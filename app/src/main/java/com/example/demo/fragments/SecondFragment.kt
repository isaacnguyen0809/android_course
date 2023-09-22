package com.example.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.demo.R
import com.example.demo.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment() {
  private var _binding: FragmentSecondBinding? = null

  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val count = requireArguments().getInt("count")
    val name = requireArguments().getString("name")
    log("count=$count. name=$name")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    log("onCreateView")
    _binding = FragmentSecondBinding.inflate(
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

    binding.buttonToThird.setOnClickListener {
      parentFragmentManager.commit {
        replace<ThirdFragment>(
          containerViewId = R.id.fragment_container,
          tag = "ThirdFragment",
          args = null,
        )
        setReorderingAllowed(true)

        addToBackStack("SecondFragment_to_ThirdFragment")
      }
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}