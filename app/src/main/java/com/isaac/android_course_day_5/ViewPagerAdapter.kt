package com.isaac.android_course_day_5

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.isaac.android_course_day_5.fragment.AddNoteFragment
import com.isaac.android_course_day_5.fragment.AllNotesFragment
import com.isaac.android_course_day_5.fragment.LoginFragment
import com.isaac.android_course_day_5.fragment.NoteDetailFragment
import com.isaac.android_course_day_5.fragment.SignUpFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> SignUpFragment()
            2 -> AllNotesFragment()
            3 -> NoteDetailFragment()
            4 -> AddNoteFragment()
            else -> LoginFragment()
        }
    }
}