package com.submission.githubusersearch.network

import com.google.gson.GsonBuilder
import com.submission.githubusersearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.github.com/"

object RetrofitClient {
    fun getClient(): GithubEndpoint {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "token ${BuildConfig.TOKEN}").build()
                chain.proceed(request)
            }
            .build()

        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(GithubEndpoint::class.java)
    }
}