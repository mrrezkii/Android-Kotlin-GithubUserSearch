package com.submission.githubusersearch.storage.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableUsername")
data class ListUserEntity(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val profilePict: String?
)