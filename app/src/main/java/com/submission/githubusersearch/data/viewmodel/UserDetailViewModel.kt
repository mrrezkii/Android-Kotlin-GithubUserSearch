package com.submission.githubusersearch.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.network.GithubEndpoint
import com.submission.githubusersearch.network.Resource
import kotlinx.coroutines.launch

class UserDetailViewModel(
    val getApi: GithubEndpoint
) : ViewModel() {

    val usernameResponse: MutableLiveData<String> = MutableLiveData("")
    val userDetailResponse: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val userFollowingResponse: MutableLiveData<Resource<List<UserResponse>>> = MutableLiveData()
    val userFollowerResponse: MutableLiveData<Resource<List<UserResponse>>> = MutableLiveData()

    fun setUsername(username: String) = viewModelScope.launch {
        usernameResponse.value = username
    }

    fun fetchUserDetail(username: String) = viewModelScope.launch {
        userDetailResponse.value = Resource.Loading()
        try {
            userDetailResponse.value = Resource.Success(getApi.user(username).body()!!)
        } catch (e: Exception) {
            userDetailResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchUserFollowing(username: String) = viewModelScope.launch {
        userFollowingResponse.value = Resource.Loading()
        try {
            userFollowingResponse.value = Resource.Success(getApi.following(username).body()!!)
        } catch (e: Exception) {
            userFollowingResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchUserFollower(username: String) = viewModelScope.launch {
        userFollowerResponse.value = Resource.Loading()
        try {
            userFollowerResponse.value = Resource.Success(getApi.followers(username).body()!!)
        } catch (e: Exception) {
            userFollowerResponse.value = Resource.Error(e.message.toString())
        }
    }

}