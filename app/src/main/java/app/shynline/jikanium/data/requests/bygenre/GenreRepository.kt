package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    suspend fun getAnimeListByGenre(genre: Int, page: Int): Flow<Result<List<Anime>>>

    suspend fun refreshCache()
}