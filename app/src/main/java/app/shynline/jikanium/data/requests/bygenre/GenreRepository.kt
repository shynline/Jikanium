package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import kotlinx.coroutines.flow.Flow

/***
 * Genre repository interface
 */
interface GenreRepository {

    /***
     * Get a list of anime for a specific genre in a page
     */
    suspend fun getAnimeListByGenre(genre: Int, page: Int): Flow<Result<List<AnimePart>>>

    /***
     * Clear the in memory cache
     */
    suspend fun refreshCache()
}