package com.example.codialappdemo.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.codialappdemo.fragment.group.GroupsOpened
import com.example.codialappdemo.fragment.group.GroupsOpening

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = if (position == 0) {
           GroupsOpened()
        } else {
            GroupsOpening()
        }
        return fragment
    }
}