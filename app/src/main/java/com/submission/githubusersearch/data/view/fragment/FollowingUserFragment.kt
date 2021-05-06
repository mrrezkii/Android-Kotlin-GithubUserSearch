package com.dicoding.githubapi.data.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.data.view.adapter.UserAdapter
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.data.viewmodel.factory.UserDetailViewModelFactory
import com.submission.githubusersearch.databinding.FragmentFollowingUserBinding
import com.submission.githubusersearch.network.Resource
import com.submission.githubusersearch.network.RetrofitClient

class FollowingUserFragment : Fragment() {

    private val api by lazy { RetrofitClient.getClient() }
    private lateinit var binding: FragmentFollowingUserBinding
    private lateinit var viewModelFactory: UserDetailViewModelFactory
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupViewModel() {
        viewModelFactory = UserDetailViewModelFactory(api)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserDetailViewModel::class.java)
    }

    private fun setupListener() {
        viewModel.fetchUserFollowing("newbiexpert")
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener {
            override fun onClick(result: UserResponse) {
            }
        })
        binding.listResult.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.userFollowingResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    print("following : isLoading")
                }
                is Resource.Success -> {
                    print("following : ${it.data!!}")
                    adapter.setData(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}