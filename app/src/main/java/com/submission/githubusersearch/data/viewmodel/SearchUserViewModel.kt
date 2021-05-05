package com.submission.githubusersearch.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapi.network.response.SearchUserResponse
import com.submission.githubusersearch.network.GithubEndpoint
import com.submission.githubusersearch.network.Resource
import kotlinx.coroutines.launch

class SearchUserViewModel(
    val getApi: GithubEndpoint
) : ViewModel() {

    val searchUserResponse: MutableLiveData<Resource<SearchUserResponse>> = MutableLiveData()

    fun fetchUsername(username: String) = viewModelScope.launch {
        searchUserResponse.value = Resource.Loading()
        try {
            searchUserResponse.value = Resource.Success(getApi.search(username).body()!!)
        } catch (e: Exception) {
            searchUserResponse.value = Resource.Error(e.message.toString())
        }
    }

}