package com.dicoding.githubapi.data.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubusersearch.R
import com.submission.githubusersearch.data.view.adapter.DetailUserAdapter
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.databinding.FragmentDetailUserBinding
import com.submission.githubusersearch.network.Resource


class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(UserDetailViewModel::class.java) }
    private val username by lazy { requireArguments().getString("username")!! }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupTab()
        setupListener()
        setupObserver()
        setupFab()
    }

    private fun setupFab() {
        viewModel.dataUser.observe(viewLifecycleOwner, Observer { data ->
            binding.fab.setOnClickListener {
                val bottomSheetDialog =
                    BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater.from(activity).inflate(
                    R.layout.bottomsheet_container,
                    requireActivity().findViewById(R.id.bottom_sheet_container)
                )

                val tvUsername = bottomSheetView.findViewById<TextView>(R.id.tv_username)
                val tvName = bottomSheetView.findViewById<TextView>(R.id.tv_name)
                val tvLocation = bottomSheetView.findViewById<TextView>(R.id.tv_location)
                val tvCompany = bottomSheetView.findViewById<TextView>(R.id.tv_company)

                tvUsername.text = username
                if (data.name != null) {
                    tvName.text = data.name
                }
                if (data.location != null) {
                    tvLocation.text = data.location
                }
                if (data.company != null) {
                    tvCompany.text = data.company
                }

                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }
        })

    }

    private fun setupView() {
        binding.detailContainer.isFillViewport = true
        binding.toolbarLayout.title = username
        binding.toolbarLayout.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbarLayout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTab() {
        val tabTitles = arrayListOf(getString(R.string.follower), getString(R.string.following))
        val tabAdapter = DetailUserAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupListener() {
        viewModel.fetchUserDetail(username)
        viewModel.usernameResponse.postValue(username)
    }

    private fun setupObserver() {
        viewModel.userDetailResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    print("detail : isLoading")
                }
                is Resource.Success -> {
                    print("detail : ${it.data!!.name}")
                    binding.usernameGithub.text = it.data.name
                    viewModel.dataUser.postValue(it.data)

                    Glide.with(view?.context!!)
                        .load(it.data.avatarUrl)
                        .centerCrop()
                        .into(binding.imageViewHeader)
                }
                is Resource.Error -> {
                    Toast.makeText(context, "gagal detail", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imgr.hideSoftInputFromWindow(view?.windowToken, 0)
    }


}