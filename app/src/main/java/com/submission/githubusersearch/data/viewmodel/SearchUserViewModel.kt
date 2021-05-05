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

    val titleBar: MutableLiveData<String> = MutableLiveData("")
    val searchUserResponse: MutableLiveData<Resource<SearchUserResponse>> = MutableLiveData()

    init {
        fetchSearch()
    }

    private fun fetchSearch() = viewModelScope.launch {
        searchUserResponse.value = Resource.Loading()
        try {
            searchUserResponse.value = Resource.Success(getApi.search("q").body()!!)
        } catch (e: Exception) {
            searchUserResponse.value = Resource.Error(e.message.toString())
        }
    }

}