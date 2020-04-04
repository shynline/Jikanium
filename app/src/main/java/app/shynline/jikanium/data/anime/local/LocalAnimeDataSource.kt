package app.shynline.jikanium.data.anime.local

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.Result.Error
import app.shynline.jikanium.data.Result.Success
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/***
 * Local anime data source
 */
class LocalAnimeDataSource(
    private val animeDao: AnimeDao,
    private val scheduler: Scheduler = Schedulers.io()
) : AnimeDataSource {

    /***
     * retrieve an anime by its id
     * or return an error result
     */
    override fun getAnime(id: Long): Single<Result<Anime>> {
        return try {
            animeDao.getAnimeById(id)
                .map {
                    if (it != null) {
                        Success(it)
                    } else {
                        Error(Exception("Anime not found!"))
                    }
                }
                .subscribeOn(scheduler)
        } catch (e: Exception) {
            Single.just(Error(e))
        }
    }

    /***
     * retrieve a collection of anime by their id
     * or return an error result
     */
    override fun getAnimeCollection(id: List<Long>): Single<Result<List<Anime>>> {
        return try {
            animeDao.getAnimeCollectionById(id)
                .map {
                    if (it.isNotEmpty()) {
                        Success(it)
                    } else {
                        Error(Exception("Anime not found!"))
                    }
                }
                .subscribeOn(scheduler)
        } catch (e: Exception) {
            Single.just(Error(e))
        }
    }

    /***
     * Insert a single anime into database
     */
    override fun insertAnime(anime: Anime) {
        animeDao.insertAnime(anime)
    }

    /***
     * Insert a collection of anime into database
     */
    override fun insertCollectionOfAnime(anime: List<Anime>) {
        animeDao.insertCollectionOfAnime(anime)
    }
}