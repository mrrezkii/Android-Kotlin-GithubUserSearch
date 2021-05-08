package com.submission.githubusersearch.network

import com.kotlinmvvm.cekongkir.database.preferences.PreferencesModel
import com.kotlinmvvm.cekongkir.database.preferences.prefUsername
import com.submission.githubusersearch.storage.perferences.GithubPreferences
import com.submission.githubusersearch.storage.persistence.GithubDatabase
import com.submission.githubusersearch.storage.persistence.ListUserEntity

class GithubRepository(
    private val api: GithubEndpoint,
    private val pref: GithubPreferences,
    private val db: GithubDatabase
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

    suspend fun saveDataUsername(listUserEntity: ListUserEntity) {
        db.listUserData().insert(listUserEntity)
    }

    fun getDataUsername() = db.listUserData().select()

    suspend fun deleteDataUsername() {
        db.listUserData().deleteAll()
    }

}