package app.shynline.jikanium.data.requests.bygenre.remote

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import java.util.*

class FakeRemoteGenreDataSource : GenreDataSource {

    var existed = false
    var animes: List<Anime> = listOf()
    override suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage> {
        null!!
    }

    override suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper> {
        return if (existed) {
            val apw = AnimePageWrapper(
                animes = animes,
                genrePage = GenrePage(
                    pageNumber = page,
                    cached = true,
                    expiryDate = Date().time * 2
                )
            )
            Result.Success(apw)
        } else {
            Result.Error(RandomException())
        }
    }

    override suspend fun insertAnimeGenre(genreWithPage: GenreWithPage) {
    }

    override suspend fun updateAnimeGenre(genreWithPage: GenreWithPage) {
    }
}