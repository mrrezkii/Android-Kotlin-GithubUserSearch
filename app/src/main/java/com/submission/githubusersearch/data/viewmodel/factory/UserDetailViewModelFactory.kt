package com.submission.githubusersearch.data.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.network.GithubEndpoint

class UserDetailViewModelFactory(
    private val getApi: GithubEndpoint
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailViewModel(getApi) as T
    }
}