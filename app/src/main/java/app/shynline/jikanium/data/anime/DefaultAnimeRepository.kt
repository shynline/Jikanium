package app.shynline.jikanium.data.anime

import app.shynline.jikanium.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/***
 * Default implementation of anime repository
 */
class DefaultAnimeRepository(
    private val localAnimeDataSource: AnimeDataSource,
    private val remoteAnimeDataSource: AnimeDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AnimeRepository {


    private val cachedAnimes by lazy { ConcurrentHashMap<Long, Anime>() }

    /***
     * Get a anime by its id
     */
    override suspend fun getAnime(id: Long): Flow<Result<Anime>> = withContext(ioDispatcher) {
        return@withContext flow {
            // check cache
            // if not, check db
            // if not, req network, update db, update cache, return
            // if yea. check expiry time
            // if not, req network, update db, update cache, return
            // if yey, update cache, return
            // if yea. check expiry time
            // if not, req network, update db, update cache, return
            // if yey, return
            // Cache exists
            cachedAnimes[id]?.let {
                if (it.cached && it.expiryDate > Date().time) {
                    // Cache is valid
                    emit(Result.Success(it))
                    return@flow
                } else {
                    // Cache is invalid
                    emit(Result.Loading)
                    // requesting remote
                    val remote = remoteAnimeDataSource.getAnime(id)
                    if (remote is Result.Success) {
                        // remote response valid
                        // update local data source and cache
                        localAnimeDataSource.insertAnime(remote.data)
                        updateCache(remote.data)
                    }
                    // emit result no matter if successful or not
                    emit(remote)
                    return@flow
                }
            }
            // No cache case
            val local = localAnimeDataSource.getAnime(id)
            if (local is Result.Success) {
                // local data exists
                if (local.data.cached && local.data.expiryDate > Date().time) {
                    // local data is valid
                    updateCache(local.data)
                    emit(Result.Success(local.data))
                    return@flow
                } else {
                    // local data is invalid
                    emit(Result.Loading)
                    // requesting remote
                    val remote = remoteAnimeDataSource.getAnime(id)
                    if (remote is Result.Success) {
                        // remote response valid
                        // update local data source and cache
                        localAnimeDataSource.insertAnime(remote.data)
                        updateCache(remote.data)
                    }
                    // emit result no matter if successful or not
                    emit(remote)
                    return@flow
                }
            } else {
                // No local data
                emit(Result.Loading)
                // requesting remote
                val remote = remoteAnimeDataSource.getAnime(id)
                if (remote is Result.Success) {
                    // remote response valid
                    // update local data source and cache
                    localAnimeDataSource.insertAnime(remote.data)
                    updateCache(remote.data)
                }
                // emit result no matter if successful or not
                emit(remote)
                return@flow
            }
        }
    }


    private fun updateCache(anime: Anime) {
        cachedAnimes[anime.id] = anime.copy()
    }

    /***
     * To clean the in memory cache
     */
    override fun refreshCache() {
        cachedAnimes.clear()
    }
}