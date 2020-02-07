package app.shynline.jikanium.data.requests.bygenre.local

import app.shynline.jikanium.GenreTestUtil
import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage

class FakeLocalGenreDataSource : GenreDataSource {


    var existed = false
    var pageCount = 4000L

    override suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage> {
        return if (existed) {
            val genreO = GenreTestUtil.createAnimeGenre(genre, pageCount)
            val pagesO = GenreTestUtil.createPagesForGenre(genreO.genreId)
            Result.Success(GenreWithPage(genreO, pagesO))
        } else {
            Result.Error(RandomException())
        }
    }

    override suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper> {
        null!!
    }

    override suspend fun insertAnimeGenre(genreWithPage: GenreWithPage) {
    }

    override suspend fun updateAnimeGenre(genreWithPage: GenreWithPage) {
    }

    override suspend fun getAnimePartCollection(id: List<Long>): Result<List<AnimePart>> {
        return if (existed) {

            val data: MutableList<AnimePart> = mutableListOf()
            id.forEach {
                data.add(AnimePart(id = it))
            }
            Result.Success(data)
        } else {
            Result.Success(listOf())
        }
    }

    override suspend fun insertCollectionOfAnimePart(anime: List<AnimePart>) {
    }
}