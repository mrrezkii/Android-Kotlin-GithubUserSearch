package com.submission.githubusersearch.data.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.submission.githubusersearch.R
import com.submission.githubusersearch.data.viewmodel.SearchUserViewModel
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.data.viewmodel.factory.SearchUserViewModelFactory
import com.submission.githubusersearch.data.viewmodel.factory.UserDetailViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val searchUserViewModelFactory: SearchUserViewModelFactory by instance()
    private val userDetailViewModelFactory: UserDetailViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        ViewModelProvider(this, searchUserViewModelFactory).get(SearchUserViewModel::class.java)
        ViewModelProvider(this, userDetailViewModelFactory).get(UserDetailViewModel::class.java)
    }

}