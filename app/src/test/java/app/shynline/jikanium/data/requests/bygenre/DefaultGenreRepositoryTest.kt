package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.local.FakeLocalGenreDataSource
import app.shynline.jikanium.data.requests.bygenre.remote.FakeRemoteGenreDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/***
 * DefaultGenreRepositoryTest
 */
class DefaultGenreRepositoryTest {


    private lateinit var defaultGenreRepository: DefaultGenreRepository
    private val fakeLocalGenreDataSource = FakeLocalGenreDataSource()
    private val fakeRemoteGenreDataSource = FakeRemoteGenreDataSource()

    companion object {
        const val ANIME_ID = 69L
    }

    /***
     * Instantiating DefaultGenreRepository
     */
    @Before
    fun setUp() {
        defaultGenreRepository = DefaultGenreRepository(
            fakeLocalGenreDataSource, fakeRemoteGenreDataSource, Dispatchers.Unconfined
        )
    }


    /***
     * getAnimeListByGenre method
     * when it suppose to load from cache
     */
    @Test
    fun test_getAnimeListByGenre_loadFromCache() = runBlocking {
        defaultGenreRepository.refreshCache()
        fakeLocalGenreDataSource.existed = true
        fakeRemoteGenreDataSource.existed = false
        defaultGenreRepository.getAnimeListByGenre(1, 1).collect {}
        fakeLocalGenreDataSource.existed = false
        var first = true

        defaultGenreRepository.getAnimeListByGenre(1, 1)
            .collect {
                if (first) {
                    first = false
                    assertThat(it).isInstanceOf(Result.Loading::class.java)
                } else {
                    assertThat(it).isInstanceOf(Result.Success::class.java)
                }
            }
    }

    /***
     * getAnimeListByGenre method
     * when it suppose to load from local data base
     */
    @Test
    fun test_getAnimeListByGenre_loadFromLocal() = runBlocking {
        defaultGenreRepository.refreshCache()
        fakeLocalGenreDataSource.existed = true
        fakeRemoteGenreDataSource.existed = false
        var first = true
        defaultGenreRepository.getAnimeListByGenre(1, 1)
            .collect {
                if (first) {
                    first = false
                    assertThat(it).isInstanceOf(Result.Loading::class.java)
                } else {
                    assertThat(it).isInstanceOf(Result.Success::class.java)
                }
            }
    }

    /***
     * getAnimeListByGenre method
     * when it suppose to load data from api
     */
    @Test
    fun test_getAnimeListByGenre_loadFromRemote() = runBlocking {
        defaultGenreRepository.refreshCache()
        fakeLocalGenreDataSource.existed = false
        fakeRemoteGenreDataSource.existed = true
        var first = true
        defaultGenreRepository.getAnimeListByGenre(1, 1)
            .collect {
                if (first) {
                    first = false
                    assertThat(it).isInstanceOf(Result.Loading::class.java)
                } else {
                    assertThat(it).isInstanceOf(Result.Success::class.java)
                }
            }
    }

    /***
     * getAnimeListByGenre method
     * error case
     */
    @Test
    fun test_getAnimeListByGenre_error() = runBlocking {
        defaultGenreRepository.refreshCache()
        fakeLocalGenreDataSource.existed = false
        fakeRemoteGenreDataSource.existed = false
        var first = true
        defaultGenreRepository.getAnimeListByGenre(1, 1)
            .collect {
                if (first) {
                    first = false
                    assertThat(it).isInstanceOf(Result.Loading::class.java)
                } else {
                    assertThat(it).isInstanceOf(Result.Error::class.java)
                    val r = it as Result.Error
                    assertThat(r.exception).isInstanceOf(RandomException::class.java)
                }
            }
    }
}