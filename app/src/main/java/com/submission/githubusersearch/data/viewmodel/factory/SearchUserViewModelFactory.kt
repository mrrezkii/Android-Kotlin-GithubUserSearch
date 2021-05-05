package com.submission.githubusersearch.data.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.githubusersearch.data.viewmodel.SearchUserViewModel
import com.submission.githubusersearch.network.GithubEndpoint

class SearchUserViewModelFactory(
    private val getApi: GithubEndpoint
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchUserViewModel(getApi) as T
    }
}