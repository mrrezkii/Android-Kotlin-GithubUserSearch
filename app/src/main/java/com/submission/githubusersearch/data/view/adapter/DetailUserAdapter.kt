package com.submission.githubusersearch.data.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubapi.data.view.fragment.FollowerUserFragment
import com.dicoding.githubapi.data.view.fragment.FollowingUserFragment

class DetailUserAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragments: ArrayList<Fragment> = arrayListOf(
        FollowerUserFragment(), FollowingUserFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}