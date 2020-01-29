package app.shynline.jikanium

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.toResult
import app.shynline.jikanium.dataset.AnimeMock
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level
import java.util.logging.Logger

class JikanApiTest {


    private var mockWebServer = MockWebServer()
    private lateinit var jikanApi: JikanApi


    @Before
    fun setup() {
        Logger.getLogger(MockWebServer::class.java.name).level = Level.WARNING

        mockWebServer.start()
        jikanApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(JikanApi::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAnimeResponse() = runBlocking {
        val response = MockResponse().setResponseCode(200)
            .setBody(AnimeMock.animeBody)

        mockWebServer.enqueue(response)

        var res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Success::class.java)
        res = res as Result.Success
        assertThat(res.data.request_hash).matches(AnimeMock.request_hash)
        assertThat(res.data.title).matches(AnimeMock.title)
        assertThat(res.data.id).isEqualTo(AnimeMock.mal_id)

    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}