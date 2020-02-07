package app.shynline.jikanium.data.requests.bygenre.local

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.Result.Error
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.GenreDataSource
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/***
 * Local implementation of genre data source
 */
class LocalGenreDataSource @Inject constructor(
    private val genreDao: GenreDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenreDataSource {

    /***
     * Get a specific genre with its pages if exists
     * if not return Error result
     */
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

    /***
     * Insert a genre with its pages into genre dao
     */
    override suspend fun insertAnimeGenre(genreWithPage: GenreWithPage) =
        withContext(ioDispatcher) {
            genreDao.insertGenre(genreWithPage.genre)
            genreDao.insertPages(genreWithPage.pages)
        }

    /***
     * Update a genre with its pages into genre dao
     */
    override suspend fun updateAnimeGenre(genreWithPage: GenreWithPage) {
        genreDao.updateGenre(genreWithPage.genre)
        genreDao.updatePages(genreWithPage.pages)
    }

    /***
     * Not supported here
     */
    override suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper> {
        throw RuntimeException("Not supported!")
    }

    /***
     * Get anime part collection
     */
    override suspend fun getAnimePartCollection(id: List<Long>): Result<List<AnimePart>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val anime = genreDao.getAnimePartCollectionById(id)
                if (anime.isNotEmpty()) {
                    Result.Success(anime)
                } else {
                    Error(Exception("Anime not found!"))
                }
            } catch (e: Exception) {
                Error(e)
            }
        }

    /***
     * Insert a collection of anime part
     */
    override suspend fun insertCollectionOfAnimePart(anime: List<AnimePart>) {
        genreDao.insertCollectionOfAnimePart(anime)
    }

}