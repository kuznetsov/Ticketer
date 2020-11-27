package com.elliotgrin.ticketer.util.api

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiUtils {

    private const val TIMEOUT_CONNECT = 60L
    private const val TIMEOUT_READ = 60L
    private const val TIMEOUT_WRITE = 60L

    fun createHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // Timeout settings
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        }.build()
    }

    fun createGsonConverter(): Converter.Factory {
        val builder = GsonBuilder()
        builder.setLenient()
        builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        return GsonConverterFactory.create(builder.create())
    }

    fun createRetrofit(client: OkHttpClient, converter: Converter.Factory, url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }

    inline fun <reified T> getApi(retrofit: Retrofit): T = retrofit.create(T::class.java)

}
