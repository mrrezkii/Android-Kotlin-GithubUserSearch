package com.submission.githubusersearch.network

class GithubRepository(
    private val api: GithubEndpoint,
) {

    suspend fun fetchSearch(username: String?) = api.search(username)

    suspend fun fetchUser(username: String?) = api.user(username)

    suspend fun fetchFollowing(username: String?) = api.following(username)

    suspend fun fetchFollower(username: String?) = api.followers(username)

}