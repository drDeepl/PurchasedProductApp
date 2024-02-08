package com.mypurchasedproduct.di.modules

import android.content.Context
import android.util.Log
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.data.remote.PurchasedProductAppApi
import com.mypurchasedproduct.presentation.utils.Constants.Companion.AUTH_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.BASE_URL
import com.mypurchasedproduct.presentation.utils.JwtTokenUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val refreshTokenPattern = "http://85.209.9.101:3000/api/auth/refresh".toRegex()

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun providesOkttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, dataStoreManager: DataStoreManager ) = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(Interceptor {chain: Interceptor.Chain ->
            var request = chain.request()
            if(!refreshTokenPattern.containsMatchIn(chain.request().url.toString())){
                val token: String? = runBlocking {
                    dataStoreManager.getAccessToken().first()
                }
                Log.wtf("NETWORK INTERCEPTOR", "TOKEN $token")
                request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}").build()
            }
            Log.wtf("NETWORK INTERCEPTOR", "AUTH ${chain.request().header("Authorization")}")
            chain.proceed(request)

        })
        .build()




    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Singleton
    @Provides
    fun providesPurchasedProductApi(retrofit: Retrofit) = retrofit.create(PurchasedProductAppApi::class.java)




}