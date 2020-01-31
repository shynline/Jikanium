package app.shynline.jikanium.data.requests.bygenre.remote

import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import app.shynline.jikanium.data.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/***
 * Remote implementation of genre data source
 */
class RemoteGenreDataSource @Inject constructor(
    private val jikanApi: JikanApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenreDataSource {
    /***
     * Fetch the list of anime in a specific genre by page
     */
    override suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper> =
        withContext(ioDispatcher) {
            return@withContext try {
                val raw = jikanApi.getAnimeListByGenre(genre, page)
                val res = raw.toResult()
                if (res is Result.Success) {
                    res.data.genrePage.updateCache(
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
     * Not supported here
     */
    override suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage> {
        throw RuntimeException("Not supported by API")
    }

    /***
     * Not supported here
     */
    override suspend fun insertAnimeGenre(genreWithPage: GenreWithPage) {
        throw RuntimeException("Not supported by API")
    }

    /***
     * Not supported here
     */
    override suspend fun updateAnimeGenre(genreWithPage: GenreWithPage) {
        throw RuntimeException("Not supported by API")
    }
}