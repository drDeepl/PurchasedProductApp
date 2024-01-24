package com.mypurchasedproduct.di.modules

import android.content.Context
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.data.remote.PurchasedProductAppApi
import com.mypurchasedproduct.presentation.utils.Constants.Companion.BASE_URL
import com.mypurchasedproduct.presentation.utils.JwtTokenUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
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
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var accessToken: String? = null
                runBlocking {
                    GlobalScope.launch {
                        dataStoreManager.getAccessToken().collect{
                            accessToken = it
                        }
                    }
                }
                val request = chain.request().newBuilder().addHeader("Authorization", "${accessToken}").build()
                return chain.proceed(request)
            }
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