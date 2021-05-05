package com.dicoding.githubapi.data.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.submission.githubusersearch.databinding.FragmentFollowerUserBinding

class FollowerUserFragment : Fragment() {

    private lateinit var binding: FragmentFollowerUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerUserBinding.inflate(inflater, container, false)
        return binding.root
    }

}