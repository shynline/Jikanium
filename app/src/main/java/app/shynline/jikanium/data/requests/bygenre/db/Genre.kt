package app.shynline.jikanium.data.requests.bygenre.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/***
 * Genre cache for api request entity
 */
@Entity(tableName = "genres")
data class Genre(

    @ColumnInfo(name = "genre_id")
    @PrimaryKey
    var genreId: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "type")
    var type: Int = 0,

    @ColumnInfo(name = "genre")
    var genre: Int = 0,

    @ColumnInfo(name = "count")
    var count: Long = 0L


)