package app.shynline.jikanium.data.anime

import app.shynline.jikanium.RandomException
import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.anime.local.FakeLocalAnimeDataSource
import app.shynline.jikanium.data.anime.remote.FakeRemoteAnimeDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultAnimeRepositoryTest {

    private val fakeLocalAnimeDataSource = FakeLocalAnimeDataSource()
    private val fakeRemoteAnimeDataSource = FakeRemoteAnimeDataSource()
    private lateinit var defaultAnimeRepository: DefaultAnimeRepository

    companion object {
        const val ANIME_ID = 69L
    }

    @Before
    fun setUp() {
        defaultAnimeRepository = DefaultAnimeRepository(
            fakeLocalAnimeDataSource,
            fakeRemoteAnimeDataSource,
            Dispatchers.Unconfined
        )
    }

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

    @After
    fun tearDown() {
    }
}