package app.shynline.jikanium.data.anime.remote

import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import app.shynline.jikanium.data.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/***
 * Remote anime data source
 */
class RemoteAnimeDataSource @Inject constructor(
    private val jikanApi: JikanApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AnimeDataSource {

    /***
     * This method should not be called
     */
    override suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>> {
        throw RuntimeException("Not supported by API")
    }


    /***
     * Fetch an anime by id from remote api
     */
    override suspend fun getAnime(id: Long): Result<Anime> = withContext(ioDispatcher) {
        return@withContext try {
            val raw = jikanApi.getAnime(id)
            val res = raw.toResult()
            if (res is Result.Success) {
                res.data.updateCache(
                    raw.headers()["X-Request-Cached"],
                    raw.headers()["X-Request-Cache-Ttl"]
                )
            }
            res
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /***
     * This method should not be called
     */
    override suspend fun insertAnime(anime: Anime) {
        throw RuntimeException("Not supported by API")
    }

    /***
     * This method should not be called
     */
    override suspend fun insertCollectionOfAnime(anime: List<Anime>) {
        throw RuntimeException("Not supported by API")
    }
}