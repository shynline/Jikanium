package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result

/***
 * Data source interface for anime
 */
interface AnimeDataSource {
    /***
     * Local data source method specific
     * and will return an anime by id
     */
    suspend fun getAnime(id: Long): Result<Anime>

    /***
     * Remote data source method specific
     * and will return an anime by id
     */
    suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>>

    /***
     * Local data source method specific
     * and will insert an anime into database
     */
    suspend fun insertAnime(anime: Anime)

    /***
     * Local data source method specific
     * and will insert a collection of anime into database
     */
    suspend fun insertCollectionOfAnime(anime: List<Anime>)
}