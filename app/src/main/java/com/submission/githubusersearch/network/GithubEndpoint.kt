package com.submission.githubusersearch.network

import com.dicoding.githubapi.network.response.FollowersResponse
import com.dicoding.githubapi.network.response.FollowingResponse
import com.dicoding.githubapi.network.response.SearchUserResponse
import com.dicoding.githubapi.network.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubEndpoint {

    @GET("users/{username}")
    suspend fun user(@Path("username") username: String?): Response<UserResponse>

    @GET("/search/users")
    suspend fun search(@Query("q") username: String?): Response<SearchUserResponse>

    @GET("users/{username}/followers")
    suspend fun followers(@Path("username") username: String?): Response<FollowersResponse>

    @GET("users/{username}/following")
    suspend fun following(@Path("username") username: String?): Response<FollowingResponse>
}