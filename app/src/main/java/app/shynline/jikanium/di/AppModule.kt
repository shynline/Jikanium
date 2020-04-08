package app.shynline.jikanium.di

import app.shynline.jikanium.data.anime.AnimeDataSource
import app.shynline.jikanium.data.anime.AnimeRepository
import app.shynline.jikanium.data.anime.DefaultAnimeRepository
import app.shynline.jikanium.data.anime.local.LocalAnimeDataSource
import app.shynline.jikanium.data.anime.remote.RemoteAnimeDataSource
import app.shynline.jikanium.data.requests.bygenre.DefaultGenreRepository
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.GenreRepository
import app.shynline.jikanium.data.requests.bygenre.local.LocalGenreDataSource
import app.shynline.jikanium.data.requests.bygenre.remote.RemoteGenreDataSource
import app.shynline.jikanium.ui.anime.AnimeViewModel
import app.shynline.jikanium.ui.animelistbygenre.AnimeListByGenreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

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

