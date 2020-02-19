package ru.app.incredible.pets.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import ru.app.incredible.pets.system.ResourceHelper
import java.util.*

object AppModule {

    fun create() = module {
        single { createMoshi() }
        single { ResourceHelper(get()) }
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}