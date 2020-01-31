package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result
import kotlinx.coroutines.flow.Flow

/***
 * Anime repository database
 */
interface AnimeRepository {
    /***
     * Get a anime by its id
     */
    suspend fun getAnime(id: Long): Flow<Result<Anime>>

    /***
     * To clean the in memory cache
     */
    fun refreshCache()
}