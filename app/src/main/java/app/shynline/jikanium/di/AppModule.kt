package app.shynline.jikanium.di

import android.content.Context
import androidx.room.Room
import app.shynline.jikanium.Config
import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.data.JikanDataBase
import app.shynline.jikanium.data.anime.AnimeDataSource
import app.shynline.jikanium.data.anime.AnimeRepository
import app.shynline.jikanium.data.anime.DefaultAnimeRepository
import app.shynline.jikanium.data.anime.local.AnimeDao
import app.shynline.jikanium.data.anime.local.LocalAnimeDataSource
import app.shynline.jikanium.data.anime.remote.RemoteAnimeDataSource
import app.shynline.jikanium.data.requests.CacheDataBase
import app.shynline.jikanium.data.requests.bygenre.DefaultGenreRepository
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.GenreRepository
import app.shynline.jikanium.data.requests.bygenre.local.GenreDao
import app.shynline.jikanium.data.requests.bygenre.local.LocalGenreDataSource
import app.shynline.jikanium.data.requests.bygenre.remote.RemoteGenreDataSource
import app.shynline.jikanium.ui.anime.AnimeViewModel
import app.shynline.jikanium.ui.animelistbygenre.AnimeListByGenreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Top level Koin module
val appModule = module {
    // Retrofit
    single { provideRetrofit() }
    single { provideJikanApi(get()) }

    single { Dispatchers.IO }
    // DataBase
    single { provideJikanDataBase(androidContext()) }
    single { provideAnimeDao(get()) }
    single { provideCacheDataBase(androidContext()) }
    single { provideGenreDao(get()) }

    // Anime DataSource and Repository
    single<AnimeDataSource>(named("local")) { LocalAnimeDataSource(get(), get()) }
    single<AnimeDataSource>(named("remote")) { RemoteAnimeDataSource(get(), get()) }
    single<AnimeRepository> {
        DefaultAnimeRepository(get(named("local")), get(named("remote")), get())
    }

    // Genre DataSource and Repository
    single<GenreDataSource>(named("local")) { LocalGenreDataSource(get(), get()) }
    single<GenreDataSource>(named("remote")) { RemoteGenreDataSource(get(), get()) }
    single<GenreRepository> {
        DefaultGenreRepository(get(named("local")), get(named("remote")), get())
    }

    viewModel { AnimeListByGenreViewModel(get()) }
    viewModel { AnimeViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(Config.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideJikanApi(retrofit: Retrofit): JikanApi {
    return retrofit.create(JikanApi::class.java)
}

fun provideAnimeDao(dataBase: JikanDataBase): AnimeDao {
    return dataBase.animeDao()
}

fun provideJikanDataBase(applicationContext: Context): JikanDataBase {
    return Room.databaseBuilder(applicationContext, JikanDataBase::class.java, "Jikan.db")
        .build()
}

fun provideGenreDao(dataBase: CacheDataBase): GenreDao {
    return dataBase.genreDao()
}

fun provideCacheDataBase(applicationContext: Context): CacheDataBase {
    return Room.databaseBuilder(applicationContext, CacheDataBase::class.java, "Cache.db")
        .build()
}
