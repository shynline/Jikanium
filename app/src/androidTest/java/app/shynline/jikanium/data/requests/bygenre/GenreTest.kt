package app.shynline.jikanium.data.requests.bygenre

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.shynline.jikanium.GenreTestUtil
import app.shynline.jikanium.data.requests.CacheDataBase
import app.shynline.jikanium.data.requests.bygenre.local.GenreDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Test class for genreDao
 *
 */
@RunWith(AndroidJUnit4::class)
class GenreTest {

    private lateinit var genreDao: GenreDao
    private lateinit var db: CacheDataBase
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CacheDataBase::class.java).build()
        genreDao = db.genreDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    /***
     * test inserting genre with pages into database
     */
    @Test
    fun test_insertGenreWithPagesAndRetrieve() = runBlocking {
        genreDao.deleteAllGenres()
        genreDao.deleteAllPages()
        val genre = GenreTestUtil.createAnimeGenre(1, 4000)
        val pages = GenreTestUtil.createPagesForGenre(genre.genreId)

        genreDao.insertGenre(genre)
        genreDao.insertPages(pages)
        var response = genreDao.getAnimeGenre(1)

        assertThat(response).isNotNull()
        response = response!!
        assertThat(response.genre).isEqualTo(genre)
        for (p in pages) {
            assertThat(response.pages).contains(p)
        }
    }

    /***
     * test updating genre with pages in database
     */
    @Test
    fun test_updateGenreWithPagesAndRetrieve() = runBlocking {
        genreDao.deleteAllGenres()
        genreDao.deleteAllPages()
        val genre = GenreTestUtil.createAnimeGenre(1, 4000)
        val pages = GenreTestUtil.createPagesForGenre(genre.genreId)
        genreDao.insertGenre(genre)
        genreDao.insertPages(pages)

        var response = genreDao.getAnimeGenre(1)
        assertThat(response).isNotNull()
        response = response!!
        response.pages[0].setItems(listOf())
        response.genre.count = 500

        genreDao.updatePages(response.pages)
        genreDao.updateGenre(response.genre)

        var response2 = genreDao.getAnimeGenre(1)
        assertThat(response2).isNotNull()
        response2 = response2!!

        assertThat(response2.genre).isEqualTo(response.genre)
        for (p in response.pages) {
            assertThat(response.pages).contains(p)
        }
    }

}