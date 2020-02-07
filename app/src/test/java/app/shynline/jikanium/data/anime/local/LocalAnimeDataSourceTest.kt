package app.shynline.jikanium.data.anime.local

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.Anime
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/***
 * LocalAnimeDataSourceTest
 */
class LocalAnimeDataSourceTest {

    companion object {
        const val ANIME_ID = 69L
    }

    private var animeDao = mockk<AnimeDao>()
    private lateinit var localAnimeDataSource: LocalAnimeDataSource

    /***
     * Instantiating LocalAnimeDataSource
     */
    @Before
    fun setUp() {
        localAnimeDataSource = LocalAnimeDataSource(animeDao, Dispatchers.Unconfined)
    }

    /***
     * getAnime method
     * throws an exception
     */
    @Test
    fun test_getAnime_APIThrowsException() = runBlocking {
        coEvery { animeDao.getAnimeById(any()) } throws RandomException()
        var response = localAnimeDataSource.getAnime(1L)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(RandomException::class.java)
    }

    /***
     * getAnime method
     * returns successfully
     */
    @Test
    fun test_getAnime_APISuccessful() = runBlocking {
        coEvery { animeDao.getAnimeById(any()) } returns Anime(id = ANIME_ID)
        var response = localAnimeDataSource.getAnime(1L)

        assertThat(response).isInstanceOf(Result.Success::class.java)
        response = response as Result.Success
        assertThat(response.data.id).isEqualTo(ANIME_ID)
    }

    /***
     * getAnime method
     * returns null
     */
    @Test
    fun test_getAnime_APIReturnsNull() = runBlocking {
        coEvery { animeDao.getAnimeById(any()) } returns null

        var response = localAnimeDataSource.getAnime(1L)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(Exception::class.java)
        assertThat(response.exception.message).matches("Anime not found!")
    }

}