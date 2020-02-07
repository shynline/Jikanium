package app.shynline.jikanium.data.requests.bygenre.local

import androidx.room.*
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage

/**
 * Interface for caching genre related requests
 *
 */
@Dao // anime=1 manga=2
interface GenreDao {

    /***
     * Retrieve a genre with its pages for an anime
     */
    @Transaction
    @Query("SELECT * FROM genres WHERE type=1 AND genre=:genre")
    suspend fun getAnimeGenre(genre: Int): GenreWithPage?

    /***
     * Retrieve a genre with its pages for a manga
     */
    @Transaction
    @Query("SELECT * FROM genres WHERE type=2 AND genre=:genre")
    suspend fun getMangaGenre(genre: Int): GenreWithPage?

    /***
     * Insert a genre into database
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    /***
     * Update a genre
     */
    @Transaction
    @Update
    suspend fun updateGenre(genre: Genre): Int

    /***
     * Insert a collection of genre pages into database
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPages(genrePages: List<GenrePage>)

    /***
     * update a collection of genre pages into database
     */
    @Transaction
    @Update
    suspend fun updatePages(genrePages: List<GenrePage>): Int

    /***
     * Delete all genres
     */
    @Query("DELETE FROM genres")
    suspend fun deleteAllPages()

    /***
     * Delete all pages
     */
    @Query("DELETE FROM genre_page")
    suspend fun deleteAllGenres()

    /***
     * Insert collection of anime parts into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectionOfAnimePart(anime: List<AnimePart>)


    /***
     * Get collection of anime part by its id
     */
    @Query("SELECT * FROM anime_part WHERE id in (:id)")
    suspend fun getAnimePartCollectionById(id: List<Long>): List<AnimePart>

}