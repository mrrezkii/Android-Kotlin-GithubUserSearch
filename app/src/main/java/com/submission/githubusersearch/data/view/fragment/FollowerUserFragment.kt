package com.dicoding.githubapi.data.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.data.view.adapter.UserAdapter
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.databinding.FragmentFollowerUserBinding
import com.submission.githubusersearch.network.Resource
import timber.log.Timber

class FollowerUserFragment : Fragment() {

    private lateinit var binding: FragmentFollowerUserBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(UserDetailViewModel::class.java) }
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupListener() {
        binding.refreshUsername.isRefreshing = true
        viewModel.usernameResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (binding.refreshUsername.isRefreshing) {
                    viewModel.fetchUserFollower(it)
                }
            }
        })
        binding.refreshUsername.setOnRefreshListener {
            viewModel.usernameResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if (binding.refreshUsername.isRefreshing) {
                        viewModel.fetchUserFollower(it)
                    }
                }
            })
        }

    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener {
            override fun onClick(result: UserResponse) {
            }
        })
        binding.listResult.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.userFollowerResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.refreshUsername.isRefreshing = true
                    Timber.d("Follower : loading")
                }
                is Resource.Success -> {
                    binding.refreshUsername.isRefreshing = false
                    Timber.d("Follower success : ${it.data!!}")
                    adapter.setData(it.data)
                }
                is Resource.Error -> {
                    binding.refreshUsername.isRefreshing = false
                    Timber.d("Follower error : ${it.message}")
                }
            }
        })
    }

}