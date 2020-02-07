package app.shynline.jikanium.data.requests.bygenre.remote

import app.shynline.jikanium.JikanApi
import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.AnimePageWrapper
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*

/***
 * RemoteGenreDataSourceTest
 */
class RemoteGenreDataSourceTest {


    private val jikanApi = mockk<JikanApi>()
    private lateinit var remoteGenreDataSource: RemoteGenreDataSource

    /***
     * Instantiating RemoteGenreDataSource
     */
    @Before
    fun setUp() {
        remoteGenreDataSource = RemoteGenreDataSource(jikanApi, Dispatchers.Unconfined)
    }

    /***
     * getAnimeGenreByPage method
     * when throws Exception
     */
    @Test
    fun test_getAnimeGenreByPage_throwsException() = runBlocking {
        coEvery { jikanApi.getAnimeListByGenre(any(), any()) } throws RandomException()

        var response = remoteGenreDataSource.getAnimeGenreByPage(1, 1)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(RandomException::class.java)

    }

    /***
     * getAnimeGenreByPage method
     * when return successful result
     */
    @Test
    fun test_getAnimeGenreByPage_successful() = runBlocking {

        val data: MutableList<AnimePart> = mutableListOf()
        listOf<Long>(2, 3, 4).forEach {
            data.add(AnimePart(id = it))
        }
        val apw = AnimePageWrapper(
            animeParts = data,
            genrePage = GenrePage(pageNumber = 1, cached = true, expiryDate = Date().time * 2)
        )
        apw.count = 5000


        coEvery { jikanApi.getAnimeListByGenre(any(), any()) } returns Response.success(apw)

        var response = remoteGenreDataSource.getAnimeGenreByPage(1, 1)

        assertThat(response).isInstanceOf(Result.Success::class.java)
        response = response as Result.Success
        assertThat(response.data).isEqualTo(apw)

    }
}