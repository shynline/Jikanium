package app.shynline.jikanium.data.anime.remote

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import java.util.*

/***
 * FakeRemoteAnimeDataSource
 */
class FakeRemoteAnimeDataSource : AnimeDataSource {

    var existed = false
    /***/
    override suspend fun getAnime(id: Long): Result<Anime> {
        return if (existed) {
            Result.Success(Anime(id = id, cached = true, expiryDate = Date().time * 2))
        } else {
            Result.Error(RandomException())
        }
    }

    /***/
    override suspend fun insertAnime(anime: Anime) {
        // Not needed
    }

    /***/
    override suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>> {
        null!!
    }

    /***/
    override suspend fun insertCollectionOfAnime(anime: List<Anime>) {
        // Not needed
    }

}