package com.dicoding.githubapi.data.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.submission.githubusersearch.databinding.FragmentFollowingUserBinding

class FollowingUserFragment : Fragment() {

    private lateinit var binding: FragmentFollowingUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingUserBinding.inflate(inflater, container, false)
        return binding.root
    }

}