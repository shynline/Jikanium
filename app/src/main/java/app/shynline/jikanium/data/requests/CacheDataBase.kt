package app.shynline.jikanium.data.requests

import androidx.room.Database
import androidx.room.RoomDatabase
import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import app.shynline.jikanium.data.requests.bygenre.local.GenreDao


/**
 * The Room Database that contains request caches.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(
    entities = [
        Genre::class,
        GenrePage::class
    ], version = 1, exportSchema = false
)
abstract class CacheDataBase : RoomDatabase() {
    /***
     * Dao for access to genres table
     */
    abstract fun genreDao(): GenreDao
}