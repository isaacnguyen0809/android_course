package com.isaac.android_course_day_5.fragment

import com.isaac.android_course_day_5.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override fun getViewBinding(): FragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

    override fun setupViews() {
        binding.textViewSignUp
    }


}