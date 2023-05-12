package com.example.quick_cheque.di.modules

import android.content.SharedPreferences
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.remote.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(sharedPreferences: SharedPreferences): TokenInterceptor {
        return TokenInterceptor(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideRtApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): QuickChequeApi {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(QuickChequeApi::class.java)
    }
}