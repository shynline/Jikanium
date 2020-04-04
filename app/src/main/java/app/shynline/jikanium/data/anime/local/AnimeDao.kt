package app.shynline.jikanium.data.anime.local

import androidx.room.*
import app.shynline.jikanium.data.anime.Anime
import io.reactivex.Single

/**
 * Data Access Object for the Animes table.
 */
@Dao
interface AnimeDao {

    /***
     * Get an anime by its id
     */
    @Query("SELECT * FROM animes WHERE id = :id")
    fun getAnimeById(id: Long): Single<Anime>

    /***
     * Get collection of anime by its id
     */
    @Query("SELECT * FROM animes WHERE id in (:id)")
    fun getAnimeCollectionById(id: List<Long>): Single<List<Anime>>

    /***
     * Insert an anime into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: Anime)

    /***
     * Insert collection of anime into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectionOfAnime(anime: List<Anime>)

    /***
     * Update an anime
     */
    @Update
    fun updateAnime(anime: Anime): Single<Int>

    /***
     * Delete all records in animes table
     */
    @Query("DELETE FROM animes")
    fun deleteAllAnime()

    /***
     * Delete a single anime by its id
     */
    @Query("DELETE FROM animes WHERE id = :id")
    fun deleteAnimeById(id: Long): Single<Int>
}