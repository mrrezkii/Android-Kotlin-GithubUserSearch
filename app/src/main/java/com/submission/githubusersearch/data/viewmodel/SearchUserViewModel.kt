package com.submission.githubusersearch.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapi.network.response.SearchUserResponse
import com.kotlinmvvm.cekongkir.database.preferences.PreferencesModel
import com.submission.githubusersearch.network.GithubRepository
import com.submission.githubusersearch.network.Resource
import kotlinx.coroutines.launch

class SearchUserViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    val searchUserResponse: MutableLiveData<Resource<SearchUserResponse>> = MutableLiveData()
    val preferences: MutableLiveData<PreferencesModel> = MutableLiveData()

    fun fetchUsername(username: String) = viewModelScope.launch {
        searchUserResponse.value = Resource.Loading()
        try {
            val response = repository.fetchSearch(username)
            searchUserResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            searchUserResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun savePreferences(username: String?) {
        repository.savePreferences(username)
    }

    fun getPreferences() {
        preferences.value = repository.getPreferences()
    }
}