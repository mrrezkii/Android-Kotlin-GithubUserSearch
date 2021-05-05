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
import com.submission.githubusersearch.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupSoftKeyboard()
    }

    private fun setupView() {
        binding.editSearch.requestFocus()
        binding.toolbar.toolbarLayout.title = "Result User"
        binding.toolbar.toolbarLayout.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.toolbarLayout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDetail.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_detailUserFragment)
        }

    }

    private fun setupSoftKeyboard() {
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        imgr.showSoftInput(binding.editSearch, 0)
    }

}