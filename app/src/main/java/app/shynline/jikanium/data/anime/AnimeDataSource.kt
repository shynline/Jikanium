package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result
import io.reactivex.Single

/***
 * Data source interface for anime
 */
interface AnimeDataSource {
    /***
     * Local data source method specific
     * and will return an anime by id
     */
    fun getAnime(id: Long): Single<Result<Anime>>

    /***
     * Remote data source method specific
     * and will return an anime by id
     */
    fun getAnimeCollection(id: List<Long>): Single<Result<List<Anime>>>

    /***
     * Local data source method specific
     * and will insert an anime into database
     */
    fun insertAnime(anime: Anime)

    /***
     * Local data source method specific
     * and will insert a collection of anime into database
     */
    fun insertCollectionOfAnime(anime: List<Anime>)
}