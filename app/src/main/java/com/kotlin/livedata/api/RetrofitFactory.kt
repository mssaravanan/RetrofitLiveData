package com.kotlin.livedata.api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder().build()
                chain.proceed(request)
            }.build()


    private val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())


    fun <S> createService(serviceClass: Class<S>, BASE_URL: String): S {
        val retrofit = builder.client(httpClient).baseUrl(BASE_URL).build()
        return retrofit.create(serviceClass)
    }
}
