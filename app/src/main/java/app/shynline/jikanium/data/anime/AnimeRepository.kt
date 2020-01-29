package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnime(id: Long): Flow<Result<Anime>>

    fun refreshCache()
}