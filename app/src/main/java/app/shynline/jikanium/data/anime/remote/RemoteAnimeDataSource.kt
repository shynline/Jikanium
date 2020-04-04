package app.shynline.jikanium.data.anime.remote

import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import app.shynline.jikanium.data.toResult
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/***
 * Remote anime data source
 */
class RemoteAnimeDataSource(
    private val jikanApi: JikanApi,
    private val scheduler: Scheduler = Schedulers.io()
) : AnimeDataSource {

    /***
     * This method should not be called
     */
    override fun getAnimeCollection(id: List<Long>): Single<Result<List<Anime>>> {
        throw RuntimeException("Not supported by API")
    }


    /***
     * Fetch an anime by id from remote api
     */
    override fun getAnime(id: Long): Single<Result<Anime>> {
        return try {
            jikanApi.getAnime(id)
                .map {
                    val res = it.toResult()
                    if (res is Result.Success) {
                        res.data.updateCache(
                            it.headers()["X-Request-Cached"],
                            it.headers()["X-Request-Cache-Ttl"]
                        )
                    }
                    res
                }
                .subscribeOn(scheduler)
        } catch (e: Exception) {
            Single.just(Result.Error(e))
        }
    }

    /***
     * This method should not be called
     */
    override fun insertAnime(anime: Anime) {
        throw RuntimeException("Not supported by API")
    }

    /***
     * This method should not be called
     */
    override fun insertCollectionOfAnime(anime: List<Anime>) {
        throw RuntimeException("Not supported by API")
    }
}