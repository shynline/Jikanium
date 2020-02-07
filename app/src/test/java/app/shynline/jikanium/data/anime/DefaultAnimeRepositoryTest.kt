package app.shynline.jikanium.data.anime

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.local.FakeLocalAnimeDataSource
import app.shynline.jikanium.data.anime.remote.FakeRemoteAnimeDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/***
 * DefaultAnimeRepositoryTest
 */
class DefaultAnimeRepositoryTest {

    /**
     * Instantiating Fake data source
     */
    private val fakeLocalAnimeDataSource = FakeLocalAnimeDataSource()
    private val fakeRemoteAnimeDataSource = FakeRemoteAnimeDataSource()

    private lateinit var defaultAnimeRepository: DefaultAnimeRepository

    companion object {
        const val ANIME_ID = 69L
    }

    /***
     * Instantiating DefaultAnimeRepository
     */
    @Before
    fun setUp() {
        defaultAnimeRepository = DefaultAnimeRepository(
            fakeLocalAnimeDataSource,
            fakeRemoteAnimeDataSource,
            Dispatchers.Unconfined
        )
    }

    /***
     * getAnime method
     * when localData is not expired
     */
    @Test
    fun test_getAnime_localDataNotExpired() = runBlocking {
        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = true
        fakeLocalAnimeDataSource.expired = false
        fakeRemoteAnimeDataSource.existed = false
        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            assertThat(it is Result.Success)
            val t = it as Result.Success
            assertThat(t.data.id).isEqualTo(ANIME_ID)
        }

    }

    /***
     * getAnime method
     * when Local Data is expired
     */
    @Test
    fun test_getAnime_localDataExpired() = runBlocking {
        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = true
        fakeLocalAnimeDataSource.expired = true
        fakeRemoteAnimeDataSource.existed = true
        var loading = true
        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            if (loading) {
                assertThat(it is Result.Loading)
                loading = false
            } else {
                assertThat(it is Result.Success)
                val t = it as Result.Success
                assertThat(t.data.id).isEqualTo(ANIME_ID)
            }
        }

    }


    /***
     * getAnime method
     * wen local data is expired and remote returns an error
     */
    @Test
    fun test_getAnime_localDataExpired_remoteError() = runBlocking {
        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = true
        fakeLocalAnimeDataSource.expired = true
        fakeRemoteAnimeDataSource.existed = false
        var loading = true
        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            if (loading) {
                assertThat(it is Result.Loading)
                loading = false
            } else {
                assertThat(it is Result.Error)
                val t = it as Result.Error
                assertThat(t.exception).isInstanceOf(RandomException::class.java)
            }
        }

    }

    /***
     * getAnime method
     * when remote returns an error
     */
    @Test
    fun test_getAnime_remoteError() = runBlocking {
        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = false
        fakeLocalAnimeDataSource.expired = false
        fakeRemoteAnimeDataSource.existed = false
        var loading = true
        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            if (loading) {
                assertThat(it is Result.Loading)
                loading = false
            } else {
                assertThat(it is Result.Error)
                val t = it as Result.Error
                assertThat(t.exception).isInstanceOf(RandomException::class.java)
            }
        }

    }

    /***
     * getAnime method
     * when remote returns data
     */
    @Test
    fun test_getAnime_remoteData() = runBlocking {
        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = false
        fakeLocalAnimeDataSource.expired = false
        fakeRemoteAnimeDataSource.existed = true
        var loading = true
        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            if (loading) {
                assertThat(it is Result.Loading)
                loading = false
            } else {
                assertThat(it is Result.Success)
                val t = it as Result.Success
                assertThat(t.data.id).isEqualTo(ANIME_ID)
            }
        }

    }


    /***
     * getAnime method
     * when data is cached
     */
    @Test
    fun test_getAnime_cachedData() = runBlocking {

        defaultAnimeRepository.refreshCache()
        fakeLocalAnimeDataSource.existed = true
        fakeLocalAnimeDataSource.expired = false
        fakeRemoteAnimeDataSource.existed = false
        defaultAnimeRepository.getAnime(ANIME_ID).collect {}
        fakeRemoteAnimeDataSource.existed = false
        fakeLocalAnimeDataSource.existed = false

        defaultAnimeRepository.getAnime(ANIME_ID).collect {
            assertThat(it is Result.Success)
            val t = it as Result.Success
            assertThat(t.data.id).isEqualTo(ANIME_ID)
        }
    }

}