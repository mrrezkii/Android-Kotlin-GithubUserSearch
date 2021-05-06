package com.dicoding.githubapi.data.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.data.view.adapter.UserAdapter
import com.submission.githubusersearch.data.viewmodel.SearchUserViewModel
import com.submission.githubusersearch.data.viewmodel.factory.SearchUserViewModelFactory
import com.submission.githubusersearch.databinding.FragmentResultBinding
import com.submission.githubusersearch.network.Resource
import com.submission.githubusersearch.network.RetrofitClient


class ResultFragment : Fragment() {

    private val api by lazy { RetrofitClient.getClient() }
    private lateinit var binding: FragmentResultBinding
    private lateinit var viewModelFactory: SearchUserViewModelFactory
    private lateinit var viewModel: SearchUserViewModel
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
        setupSoftKeyboard()
        setupViewModel()
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupView() {
        binding.editSearch.requestFocus()
        binding.toolbar.toolbarLayout.title = "Result User"
        binding.toolbar.toolbarLayout.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.toolbarLayout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun setupSoftKeyboard() {
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        imgr.showSoftInput(binding.editSearch, 0)
    }

    private fun setupViewModel() {
        viewModelFactory = SearchUserViewModelFactory(api)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchUserViewModel::class.java)
    }

    private fun setupListener() {
        viewModel.fetchUsername("newbie")
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener {
            override fun onClick(result: UserResponse) {
                TODO("Not yet implemented")
            }
        })
        binding.listResult.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.searchUserResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    print("github : isLoading")
                }
                is Resource.Success -> {
                    print("github : ${it.data!!.items}")
                    adapter.setData(it.data.items as List<UserResponse>)
                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}