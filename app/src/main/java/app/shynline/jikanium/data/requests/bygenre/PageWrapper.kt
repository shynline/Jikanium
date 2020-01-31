package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.anime.Anime
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage
import com.google.gson.annotations.SerializedName

data class AnimePageWrapper(
    var genrePage: GenrePage,
    @SerializedName("anime")
    var animes: List<Anime>,

    @SerializedName("item_count")
    var count: Long = 0L
)