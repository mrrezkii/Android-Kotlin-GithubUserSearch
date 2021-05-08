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
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.R
import com.submission.githubusersearch.data.view.adapter.UserAdapter
import com.submission.githubusersearch.data.viewmodel.SearchUserViewModel
import com.submission.githubusersearch.databinding.FragmentResultBinding
import com.submission.githubusersearch.network.Resource


class ResultFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SearchUserViewModel::class.java) }
    private lateinit var binding: FragmentResultBinding
    private lateinit var adapter: UserAdapter

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
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPreferences()
    }

    @SuppressLint("NewApi")
    private fun setupView() {
        binding.toolbarLayout.title = getString(R.string.result_username)
        binding.searchUsername.focusable
        binding.searchUsername.isIconified = false
    }

    private fun setupListener() {
        viewModel.preferences.observe(viewLifecycleOwner, Observer {
            binding.searchUsername.setQuery(it.login, false)
        })
        binding.searchUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchUsername.clearFocus()
                filterAdapter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterAdapter(newText)
                return false
            }
        })
    }

    private fun filterAdapter(query: String?) {
        adapter.filter.filter(query)
        viewModel.fetchUsername(query!!)
        binding.refreshUsername.setOnRefreshListener {
            viewModel.fetchUsername(query)
        }
        viewModel.savePreferences(query)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener {
            override fun onClick(result: UserResponse) {
                findNavController().navigate(
                    R.id.action_resultFragment_to_detailUserFragment,
                    bundleOf("username" to result.login)
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
                    binding.refreshUsername.isRefreshing = false
                    print("github : ${it.data!!.items}")
                    adapter.setData(it.data.items as List<UserResponse>)
                }
                is Resource.Error -> {
                    binding.refreshUsername.isRefreshing = false
                }
            }
        })
    }

}