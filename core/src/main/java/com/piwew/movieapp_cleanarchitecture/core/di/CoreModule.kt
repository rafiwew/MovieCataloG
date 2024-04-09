package com.piwew.movieapp_cleanarchitecture.core.di

import androidx.room.Room
import com.piwew.movieapp_cleanarchitecture.core.BuildConfig
import com.piwew.movieapp_cleanarchitecture.core.data.MovieRepository
import com.piwew.movieapp_cleanarchitecture.core.data.source.local.LocalDataSource
import com.piwew.movieapp_cleanarchitecture.core.data.source.local.room.MovieDatabase
import com.piwew.movieapp_cleanarchitecture.core.data.source.remote.RemoteDataSource
import com.piwew.movieapp_cleanarchitecture.core.data.source.remote.network.ApiService
import com.piwew.movieapp_cleanarchitecture.core.domain.repository.IMovieRepository
import com.piwew.movieapp_cleanarchitecture.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("moviecatalog".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = BuildConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/${BuildConfig.PIN_1}")
            .add(hostname, "sha256/${BuildConfig.PIN_2}")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}