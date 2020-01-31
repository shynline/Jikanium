package app.shynline.jikanium.data.requests.bygenre.local

import androidx.room.*
import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage

/**
 * Interface for caching genre related requests
 *
 */
@Dao // anime=1 manga=2
interface GenreDao {

    @Transaction
    @Query("SELECT * FROM genres WHERE type=1 AND genre=:genre")
    suspend fun getAnimeGenre(genre: Int): GenreWithPage?


    @Transaction
    @Query("SELECT * FROM genres WHERE type=2 AND genre=:genre")
    suspend fun getMangaGenre(genre: Int): GenreWithPage?

    /***
     * Insert an anime into database
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    /***
     * Update an anime
     */
    @Transaction
    @Update
    suspend fun updateGenre(genre: Genre): Int

    /***
     * Insert an anime into database
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPages(genrePages: List<GenrePage>)

    /***
     * Update an anime
     */
    @Transaction
    @Update
    suspend fun updatePages(genrePages: List<GenrePage>): Int

    @Query("DELETE FROM genres")
    suspend fun deleteAllPages()

    @Query("DELETE FROM genre_page")
    suspend fun deleteAllGenres()

}