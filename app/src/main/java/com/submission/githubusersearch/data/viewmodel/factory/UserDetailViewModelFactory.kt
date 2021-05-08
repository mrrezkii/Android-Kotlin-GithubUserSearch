package com.submission.githubusersearch.data.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.githubusersearch.data.viewmodel.UserDetailViewModel
import com.submission.githubusersearch.network.GithubRepository

class UserDetailViewModelFactory(
    private val repository: GithubRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailViewModel(repository) as T
    }
}