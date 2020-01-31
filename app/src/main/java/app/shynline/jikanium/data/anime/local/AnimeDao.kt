package app.shynline.jikanium.data.anime.local

import androidx.room.*
import app.shynline.jikanium.data.anime.Anime

/**
 * Data Access Object for the Animes table.
 */
@Dao
interface AnimeDao {

    /***
     * Get an anime by its id
     */
    @Query("SELECT * FROM animes WHERE id = :id")
    suspend fun getAnimeById(id: Long): Anime?

    /***
     * Get collection of anime by its id
     */
    @Query("SELECT * FROM animes WHERE id in (:id)")
    suspend fun getAnimeCollectionById(id: List<Long>): List<Anime>

    /***
     * Insert an anime into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: Anime)

    /***
     * Insert collection of anime into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectionOfAnime(anime: List<Anime>)

    /***
     * Update an anime
     */
    @Update
    suspend fun updateAnime(anime: Anime): Int

    /***
     * Delete all records in animes table
     */
    @Query("DELETE FROM animes")
    suspend fun deleteAllAnime()

    /***
     * Delete a single anime by its id
     */
    @Query("DELETE FROM animes WHERE id = :id")
    suspend fun deleteAnimeById(id: Long): Int
}