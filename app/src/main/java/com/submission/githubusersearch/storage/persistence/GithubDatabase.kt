package com.submission.githubusersearch.storage.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ListUserEntity::class],
    exportSchema = false,
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun listUserData(): ListUserDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GithubDatabase::class.java, "GithubDatabase.db"
            )
                .allowMainThreadQueries()
                .build()
    }
}