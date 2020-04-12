package app.shynline.jikanium.data.anime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/***
 * Anime entity
 */
@Entity(tableName = "animes")
data class Anime @JvmOverloads constructor(
    @SerializedName("request_hash")
    @ColumnInfo(name = "request_hash")
    var request_hash: String = "",

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("mal_id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,

    @ColumnInfo(name = "cached")
    var cached: Boolean = false,

    @ColumnInfo(name = "expiry_date")
    var expiryDate: Long = 0L
) {
    /***
     * Update the cache state of this entity
     */
    fun updateCache(_cached: String?, ttl: String?) {
        _cached?.let { cached ->
            val c = cached == "1"
            if (c) {
                ttl?.toLong()?.let {
                    expiryDate = Date().time + it * 1000
                    this.cached = c
                }
            }
        }
    }
}