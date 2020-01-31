package app.shynline.jikanium.data.requests.bygenre.local

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.Result.Error
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalGenreDataSource @Inject constructor(
    private val genreDao: GenreDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenreDataSource {

    override suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage> =
        withContext(ioDispatcher) {
            return@withContext try {
                val genreWithPage = genreDao.getAnimeGenre(genre)
                if (genreWithPage != null) {
                    Result.Success(genreWithPage)
                } else {
                    Error(Exception("GenreWithPage not found!"))
                }
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun insertAnimeGenre(genreWithPage: GenreWithPage) =
        withContext(ioDispatcher) {
            genreDao.insertGenre(genreWithPage.genre)
            genreDao.insertPages(genreWithPage.pages)
        }

    override suspend fun updateAnimeGenre(genreWithPage: GenreWithPage) {
        genreDao.updateGenre(genreWithPage.genre)
        genreDao.updatePages(genreWithPage.pages)
    }

    override suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper> {
        throw RuntimeException("Not supported!")
    }

}