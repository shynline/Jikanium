package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.AnimeDataSource
import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import app.shynline.jikanium.di.application.ApplicationModule.LocalDataSource
import app.shynline.jikanium.di.application.ApplicationModule.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class DefaultGenreRepository @Inject constructor(
    @LocalDataSource private val localAnimeDataSource: AnimeDataSource,
    @LocalDataSource private val localGenreDataSource: GenreDataSource,
    @RemoteDataSource private val remoteGenreDataSource: GenreDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GenreRepository {

    private val cachedPages by lazy { ConcurrentHashMap<Int, GenreWithPage>() }

    override suspend fun getAnimeListByGenre(genre: Int, page: Int): Flow<Result<List<Anime>>> =
        withContext(ioDispatcher) {
            return@withContext flow<Result<List<Anime>>> {
                emit(Result.Loading)
                // check if cache exists
                if (!cachedPages.contains(genre)) {
                    val localPages = localGenreDataSource.getAnimeGenre(genre)
                    if (localPages is Result.Success) {
                        // If cache is exists in database we load it into memory
                        cachedPages[genre] = localPages.data
                    }
                }
                //
                var genreWithPage = cachedPages[genre]
                if (genreWithPage == null) {
                    // this means both local db and cache doesn't have the genre
                    assert(page == 1) // It really doesn't matter but for maintaining our insanity
                    val remoteData = remoteGenreDataSource.getAnimeGenreByPage(genre, page)
                    if (remoteData is Result.Success) {
                        // Insert anime list into database
                        localAnimeDataSource.insertCollectionOfAnime(remoteData.data.animes)
                        // creating new genre and page objects
                        val genreLocal =
                            Genre(type = 1, genre = genre, count = remoteData.data.count)
                        val pageLocal = remoteData.data.genrePage
                        pageLocal.genreOwnerId = genreLocal.genreId
                        pageLocal.pageNumber = page
                        pageLocal.setItems(remoteData.data.animes.map { it.id })
                        //
                        // Assigning the new object to genreWithPage
                        genreWithPage = GenreWithPage(genreLocal, listOf(pageLocal))

                        // updating local database and cache
                        localGenreDataSource.insertAnimeGenre(genreWithPage)
                        cachedPages[genre] = genreWithPage
                    } else {
                        emit(Result.Error(if (remoteData is Result.Error) remoteData.exception else Exception()))
                        return@flow
                    }
                }
                // genreWithPage is not null now
                var thePage = genreWithPage.pages.find {
                    it.pageNumber == page
                }
                if (thePage == null) {
                    val remoteData = remoteGenreDataSource.getAnimeGenreByPage(genre, page)
                    if (remoteData is Result.Success) {
                        // Insert anime list into database
                        localAnimeDataSource.insertCollectionOfAnime(remoteData.data.animes)
                        // creating new genre and page objects
                        thePage = remoteData.data.genrePage
                        thePage.genreOwnerId = genreWithPage.genre.genreId
                        thePage.pageNumber = page
                        thePage.setItems(remoteData.data.animes.map { it.id })
                        //
                        // Assigning the new object to genreWithPage
                        val list: MutableList<GenrePage> = mutableListOf()
                        list.addAll(genreWithPage.pages)
                        list.add(thePage)
                        genreWithPage = genreWithPage.copy(pages = list)

                        // updating local database and cache
                        localGenreDataSource.updateAnimeGenre(genreWithPage)
                        cachedPages[genre] = genreWithPage
                    } else {
                        emit(Result.Error(if (remoteData is Result.Error) remoteData.exception else Exception()))
                        return@flow
                    }
                }
                //
                val animeList = localAnimeDataSource.getAnimeCollection(thePage.getItems())
                emit(animeList)
            }

        }

    override suspend fun refreshCache() {
        cachedPages.clear()
    }
}