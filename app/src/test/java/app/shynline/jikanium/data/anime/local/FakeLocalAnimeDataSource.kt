package app.shynline.jikanium.data.anime.local

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import java.util.*

class FakeLocalAnimeDataSource : AnimeDataSource {

    var existed = false
    var expired = false

    override suspend fun getAnime(id: Long): Result<Anime> {
        return if (existed) {
            if (expired) {
                Result.Success(Anime(id = id, cached = true, expiryDate = 0))
            } else {
                Result.Success(Anime(id = id, cached = true, expiryDate = Date().time * 2))
            }
        } else {
            Result.Error(RandomException())
        }
    }

    override suspend fun insertAnime(anime: Anime) {
    }

    override suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>> {
        return if (existed) {
            if (expired) {
                val data: MutableList<Anime> = mutableListOf()
                id.forEach {
                    data.add(Anime(id = it, cached = true, expiryDate = 0))
                }
                Result.Success(data)
            } else {
                val data: MutableList<Anime> = mutableListOf()
                id.forEach {
                    data.add(Anime(id = it, cached = true, expiryDate = Date().time * 2))
                }
                Result.Success(data)
            }
        } else {
            Result.Success(listOf())
        }
    }

    override suspend fun insertCollectionOfAnime(anime: List<Anime>) {

    }
}