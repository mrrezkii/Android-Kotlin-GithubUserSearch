package com.submission.githubusersearch

import android.app.Application
import com.submission.githubusersearch.data.viewmodel.factory.SearchUserViewModelFactory
import com.submission.githubusersearch.data.viewmodel.factory.UserDetailViewModelFactory
import com.submission.githubusersearch.network.GithubEndpoint
import com.submission.githubusersearch.network.GithubRepository
import com.submission.githubusersearch.network.RetrofitClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class GithubUserSearchApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GithubUserSearchApplication))

        bind<GithubEndpoint>() with singleton { RetrofitClient.getClient() }
        bind() from singleton { GithubRepository(instance()) }
        bind() from singleton { SearchUserViewModelFactory(instance()) }
        bind() from singleton { UserDetailViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }


}