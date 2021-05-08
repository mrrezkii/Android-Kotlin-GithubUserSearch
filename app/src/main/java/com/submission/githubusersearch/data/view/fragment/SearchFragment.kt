package com.dicoding.githubapi.data.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        binding.toolbar.toolbarLayout.title = getString(R.string.search_username)
    }

    private fun setupNavigate() {
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_resultFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imgr.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}