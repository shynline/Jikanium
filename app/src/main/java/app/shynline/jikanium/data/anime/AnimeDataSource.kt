package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result

interface AnimeDataSource {
    suspend fun getAnime(id: Long): Result<Anime>
    suspend fun getAnimeCollection(id: List<Long>): Result<List<Anime>>

    suspend fun insertAnime(anime: Anime)

    suspend fun insertCollectionOfAnime(anime: List<Anime>)
}