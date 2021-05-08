package com.submission.githubusersearch.network

import com.kotlinmvvm.cekongkir.database.preferences.PreferencesModel
import com.kotlinmvvm.cekongkir.database.preferences.prefUsername
import com.submission.githubusersearch.storage.perferences.GithubPreferences

class GithubRepository(
    private val api: GithubEndpoint,
    private val pref: GithubPreferences
) {

    suspend fun fetchSearch(username: String?) = api.search(username)

    suspend fun fetchUser(username: String?) = api.user(username)

    suspend fun fetchFollowing(username: String?) = api.following(username)

    suspend fun fetchFollower(username: String?) = api.followers(username)

    fun savePreferences(username: String?) {
        pref.put(prefUsername, username!!)
    }

    fun getPreferences(): PreferencesModel {
        return PreferencesModel(pref.getString(prefUsername))
    }

}