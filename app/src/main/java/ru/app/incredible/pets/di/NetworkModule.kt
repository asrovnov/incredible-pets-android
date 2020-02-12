package ru.app.incredible.pets.di

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.app.incredible.pets.BuildConfig
import ru.app.incredible.pets.data.backend.CatApi
import ru.app.incredible.pets.data.backend.DogApi
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NetworkModule {

    fun create() = module {
        single {
            createApi<DogApi>(
                client =  createOkHttpClient(),
                moshi = get(),
                baseUrl = BuildConfig.DOG_BASE_URL
            )
        }

        single {
            createApi<CatApi>(
                client = createOkHttpClient(),
                moshi = get(),
                baseUrl = BuildConfig.CAT_BASE_URL
            )
        }
    }

    private inline fun <reified T> createApi(
        client: OkHttpClient,
        moshi: Moshi,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(T::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        with(builder) {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addNetworkInterceptor(loggingInterceptor())
        }
        return builder.build()
    }

    private fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}