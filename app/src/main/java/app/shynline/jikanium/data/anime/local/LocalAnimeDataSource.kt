package app.shynline.jikanium.data.anime.local

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.Result.Error
import app.shynline.jikanium.data.Result.Success
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/***
 * Local anime data source
 */
class LocalAnimeDataSource(
    private val animeDao: AnimeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AnimeDataSource {

    /***
     * retrieve an anime by its id
     * or return an error result
     */
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

    /***
     * retrieve a collection of anime by their id
     * or return an error result
     */
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

    /***
     * Insert a single anime into database
     */
    override suspend fun insertAnime(anime: Anime) = withContext(ioDispatcher) {
        animeDao.insertAnime(anime)
    }

    /***
     * Insert a collection of anime into database
     */
    override suspend fun insertCollectionOfAnime(anime: List<Anime>) {
        animeDao.insertCollectionOfAnime(anime)
    }
}