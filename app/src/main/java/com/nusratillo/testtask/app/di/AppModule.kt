package com.nusratillo.testtask.app.di

import androidx.room.Room
import com.google.gson.Gson
import com.nusratillo.testtask.DB_NAME
import com.nusratillo.testtask.app.db.UserDeviceDatabase
import com.nusratillo.testtask.data.preferences.PrefManager
import com.nusratillo.testtask.data.rest.UserDevicesService
import com.nusratillo.testtask.data.rest.ServerUrls
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(ServerUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }
    single<UserDevicesService> {
        get<Retrofit>().create(UserDevicesService::class.java)
    }
    factory {
        Gson()
    }
    single {
        PrefManager(androidContext(), get())
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDeviceDatabase::class.java,
            DB_NAME
        ).build()
    }
}

val appModules = listOf(
    appModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    useCaseModule
)