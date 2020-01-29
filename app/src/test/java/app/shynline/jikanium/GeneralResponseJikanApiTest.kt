package app.shynline.jikanium

import app.shynline.jikanium.data.*
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

class GeneralResponseJikanApiTest {
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
    fun testInternalServerError() = runBlocking {
        val response = MockResponse().setResponseCode(500)
        mockWebServer.enqueue(response)


        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Error::class.java)
        assertThat((res as Result.Error).exception).isInstanceOf(InternalServerError::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testTooManyRequest() = runBlocking {
        val response = MockResponse().setResponseCode(429)
        mockWebServer.enqueue(response)

        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Error::class.java)
        assertThat((res as Result.Error).exception).isInstanceOf(TooManyRequest::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testMethodNotAllowed() = runBlocking {
        val response = MockResponse().setResponseCode(405)
        mockWebServer.enqueue(response)

        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Error::class.java)
        assertThat((res as Result.Error).exception).isInstanceOf(MethodNotAllowed::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNotFound() = runBlocking {
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)

        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Error::class.java)
        assertThat((res as Result.Error).exception).isInstanceOf(NotFound::class.java)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testBadRequest() = runBlocking {
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)

        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.Error::class.java)
        assertThat((res as Result.Error).exception).isInstanceOf(BadRequest::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNotModified() = runBlocking {
        val response = MockResponse().setResponseCode(304)
        mockWebServer.enqueue(response)

        val res = jikanApi.getAnime(1L).toResult()

        assertThat(res).isInstanceOf(Result.NotModified::class.java)
    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}