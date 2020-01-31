package app.shynline.jikanium.data.anime.local

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.Result.Error
import app.shynline.jikanium.data.Result.Success
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalAnimeDataSource @Inject constructor(
    private val animeDao: AnimeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AnimeDataSource {

    override suspend fun getAnime(id: Long): Result<Anime> = withContext(ioDispatcher) {
        return@withContext try {
            val anime = animeDao.getAnimeById(id)
            if (anime != null) {
                Success(anime)
            } else {
                Error(Exception("Anime not found!"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }


    override suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val anime = animeDao.getAnimeCollectionById(id)
                if (anime.isNotEmpty()) {
                    Success(anime)
                } else {
                    Error(Exception("Anime not found!"))
                }
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun insertAnime(anime: Anime) = withContext(ioDispatcher) {
        animeDao.insertAnime(anime)
    }

    override suspend fun insertCollectionOfAnime(anime: List<Anime>) {
        animeDao.insertCollectionOfAnime(anime)
    }
}