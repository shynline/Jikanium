package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import com.google.gson.annotations.SerializedName

/***
 * A wrapper for api -> get anime by genre request
 */
data class AnimePageWrapper(
    var genrePage: GenrePage,
    @SerializedName("anime")
    var animeParts: List<AnimePart>,

    @SerializedName("item_count")
    var count: Long = 0L
)