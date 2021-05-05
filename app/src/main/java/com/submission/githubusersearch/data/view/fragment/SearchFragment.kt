package com.dicoding.githubapi.data.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.submission.githubusersearch.R
import com.submission.githubusersearch.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupNavigate()
    }

    private fun setupView() {
        binding.toolbar.toolbarLayout.title = "Search User"
    }

    private fun setupNavigate() {
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_resultFragment)
        }
    }
}