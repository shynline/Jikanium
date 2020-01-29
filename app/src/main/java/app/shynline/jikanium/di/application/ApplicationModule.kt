package app.shynline.jikanium.di.application

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
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton


@Module(
    includes = [
        ApplicationModuleObject::class
    ]
)
abstract class ApplicationModule {

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityScope

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FragmentScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Singleton
    @LocalDataSource
    @Binds
    abstract fun bindLocalDataSource(localAnimeDataSource: LocalAnimeDataSource): AnimeDataSource

    @Singleton
    @RemoteDataSource
    @Binds
    abstract fun bindRemoteDataSource(remoteAnimeDataSource: RemoteAnimeDataSource): AnimeDataSource

    @Singleton
    @Binds
    abstract fun bindAnimeRepository(animeRepository: DefaultAnimeRepository): AnimeRepository
}


@Module
object ApplicationModuleObject {
    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    @JvmStatic
    fun provideJikanApi(retrofit: Retrofit): JikanApi {
        return retrofit.create(JikanApi::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideAnimeDao(dataBase: JikanDataBase): AnimeDao {
        return dataBase.animeDao()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideJikanDataBase(applicationContext: Context): JikanDataBase {
        return Room.databaseBuilder(applicationContext, JikanDataBase::class.java, "Jikan.db")
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO


}