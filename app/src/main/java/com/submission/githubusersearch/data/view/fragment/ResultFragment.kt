package com.submission.githubusersearch.data.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.submission.githubusersearch.R
import com.submission.githubusersearch.data.view.adapter.ResultAdapter
import com.submission.githubusersearch.data.viewmodel.SearchUserViewModel
import com.submission.githubusersearch.databinding.FragmentResultBinding
import com.submission.githubusersearch.network.Resource
import com.submission.githubusersearch.storage.persistence.ListUserEntity


class ResultFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SearchUserViewModel::class.java) }
    private lateinit var binding: FragmentResultBinding
    private lateinit var adapter: ResultAdapter

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
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPreferences()
    }

    override fun onResume() {
        super.onResume()
        viewModel.listDataUser.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    @SuppressLint("NewApi")
    private fun setupView() {
        binding.toolbarLayout.title = getString(R.string.result_username)
        binding.searchUsername.focusable
        binding.searchUsername.isIconified = false
    }

    private fun setupListener() {
        viewModel.preferences.observe(viewLifecycleOwner, Observer {
            binding.searchUsername.setQuery(it.login, true)
        })
        binding.searchUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchUsername.clearFocus()
                viewModel.fetchUsername(query!!)
                viewModel.savePreferences(query)
                binding.refreshUsername.setOnRefreshListener {
                    viewModel.fetchUsername(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.fetchUsername(newText!!)
                viewModel.savePreferences(newText)
                binding.refreshUsername.setOnRefreshListener {
                    viewModel.fetchUsername(newText)
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = ResultAdapter(arrayListOf(), object : ResultAdapter.OnAdapterListener {
            override fun onClick(result: ListUserEntity) {
                findNavController().navigate(
                    R.id.action_resultFragment_to_detailUserFragment,
                    bundleOf("username" to result.username)
                )
            }
        })
        binding.listResult.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.searchUserResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.refreshUsername.isRefreshing = true
                    print("github : isLoading")
                }
                is Resource.Success -> {
                    viewModel.deleteDataUsername()
                    val data = it.data!!.items
                    binding.refreshUsername.isRefreshing = false
                    print("github : $data")
                    data?.forEach { it ->
                        viewModel.saveDataUsername(it!!)
                    }
                }
                is Resource.Error -> {
                    binding.refreshUsername.isRefreshing = false
                }
            }
        })
    }
}