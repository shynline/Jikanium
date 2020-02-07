package app.shynline.jikanium.data.anime.remote

import app.shynline.jikanium.JikanApi
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
import retrofit2.Response

/***
 * RemoteAnimeMockDataSourceTest
 */
class RemoteAnimeMockDataSourceTest {

    companion object {
        const val ANIME_ID = 69L
    }

    private var jikanApi = mockk<JikanApi>()
    private lateinit var remoteAnimeDataSource: RemoteAnimeDataSource

    /***
     * Instantiating RemoteAnimeMockDataSource
     */
    @Before
    fun setUp() {
        remoteAnimeDataSource = RemoteAnimeDataSource(jikanApi, Dispatchers.Unconfined)

    }


    /***
     * getAnime method
     * throws an exception
     */
    @Test
    fun test_getAnime_throwsException() = runBlocking {
        coEvery { jikanApi.getAnime(any()) } throws RandomException()

        var response = remoteAnimeDataSource.getAnime(1)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(RandomException::class.java)

    }

    /***
     * getAnime method
     * returns successfully
     */
    @Test
    fun test_getAnime_successful() = runBlocking {

        coEvery { jikanApi.getAnime(any()) } returns Response.success(200, Anime(id = ANIME_ID))

        var response = remoteAnimeDataSource.getAnime(ANIME_ID)

        assertThat(response).isInstanceOf(Result.Success::class.java)
        response = response as Result.Success
        assertThat(response.data.id).isEqualTo(ANIME_ID)
    }

}