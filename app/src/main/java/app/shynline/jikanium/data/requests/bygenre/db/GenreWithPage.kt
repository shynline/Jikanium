package app.shynline.jikanium.data.requests.bygenre.db

import androidx.room.Embedded
import androidx.room.Relation

/***
 * One to many relation between genre and many genre page
 */
data class GenreWithPage(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genre_id",
        entityColumn = "genre_owner_id"
    )
    val pages: List<GenrePage>
)