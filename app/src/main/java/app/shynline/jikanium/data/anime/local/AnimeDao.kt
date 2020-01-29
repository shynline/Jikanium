package app.shynline.jikanium.data.anime.local

import androidx.room.*
import app.shynline.jikanium.data.anime.Anime

/**
 * Data Access Object for the Animes table.
 */
@Dao
interface AnimeDao {

    @Query("SELECT * FROM animes WHERE id = :id")
    suspend fun getAnimeById(id: Long): Anime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: Anime)

    @Update
    suspend fun updateAnime(anime: Anime): Int

    @Query("DELETE FROM animes")
    suspend fun deleteAllAnime()

    @Query("DELETE FROM animes WHERE id = :id")
    suspend fun deleteAnimeById(id: Long): Int
}