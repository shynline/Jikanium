package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage

interface GenreDataSource {
    suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage>

    suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper>

    suspend fun insertAnimeGenre(genreWithPage: GenreWithPage)

    suspend fun updateAnimeGenre(genreWithPage: GenreWithPage)
}