package app.shynline.jikanium.data.requests.bygenre.local

import app.shynline.jikanium.GenreTestUtil
import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class LocalGenreDataSourceTest {


    private lateinit var localGenreDataSource: LocalGenreDataSource
    private val genreDao = mockk<GenreDao>()

    @Before
    fun setUp() {
        localGenreDataSource = LocalGenreDataSource(genreDao, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_getAnimeGenre_APIThrowsException() = runBlocking {
        coEvery { genreDao.getAnimeGenre(any()) } throws RandomException()
        var response = localGenreDataSource.getAnimeGenre(1)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(RandomException::class.java)
    }

    @Test
    fun test_getAnimeGenre_APISuccessful() = runBlocking {
        val genreO = GenreTestUtil.createAnimeGenre(1, 4000)
        val pagesO = GenreTestUtil.createPagesForGenre(genreO.genreId)

        coEvery { genreDao.getAnimeGenre(any()) } returns GenreWithPage(genreO, pagesO)

        var response = localGenreDataSource.getAnimeGenre(1)

        assertThat(response).isInstanceOf(Result.Success::class.java)
        response = response as Result.Success
        assertThat(response.data.genre).isEqualTo(genreO)
        pagesO.forEach {
            assertThat(response.data.pages).contains(it)
        }
    }

    @Test
    fun test_getAnimeGenre_APIReturnsNull() = runBlocking {
        coEvery { genreDao.getAnimeGenre(any()) } returns null

        var response = localGenreDataSource.getAnimeGenre(1)

        assertThat(response).isInstanceOf(Result.Error::class.java)
        response = response as Result.Error
        assertThat(response.exception).isInstanceOf(Exception::class.java)
        assertThat(response.exception.message).matches("GenreWithPage not found!")
    }
}