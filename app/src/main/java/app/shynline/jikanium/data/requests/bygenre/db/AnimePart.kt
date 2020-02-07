package app.shynline.jikanium.data.requests.bygenre.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/***
 * Anime part entity
 */
@Entity(tableName = "anime_part")
data class AnimePart @JvmOverloads constructor(

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("mal_id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String = "",

    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",

    @SerializedName("synopsis")
    @ColumnInfo(name = "synopsis")
    var synopsis: String = "",

    @SerializedName("type")
    @ColumnInfo(name = "type")
    var type: String = "",

    @SerializedName("score")
    @ColumnInfo(name = "score")
    var score: Float = 0f

)