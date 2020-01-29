package app.shynline.jikanium.data

import androidx.room.Database
import androidx.room.RoomDatabase
import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.anime.local.AnimeDao

/**
 * The Room Database that contains the Anime table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(
    entities = [
        Anime::class
    ], version = 1, exportSchema = false
)
abstract class JikanDataBase : RoomDatabase() {
    /***
     * Dao for access to animes table
     */
    abstract fun animeDao(): AnimeDao
}