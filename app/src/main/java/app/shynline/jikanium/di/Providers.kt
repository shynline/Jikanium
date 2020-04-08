package app.shynline.jikanium.di

import android.content.Context
import androidx.room.Room
import app.shynline.jikanium.Config
import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.data.JikanDataBase
import app.shynline.jikanium.data.anime.local.AnimeDao
import app.shynline.jikanium.data.requests.CacheDataBase
import app.shynline.jikanium.data.requests.bygenre.local.GenreDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
