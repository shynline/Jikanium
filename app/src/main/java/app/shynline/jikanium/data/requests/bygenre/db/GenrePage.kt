package app.shynline.jikanium.data.requests.bygenre.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/***
 * Genre page cache for api request entity
 * represent each page of a specific genre
 */
@Entity(tableName = "genre_page")
data class GenrePage(
    @PrimaryKey
    val pageId: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "page_number")
    var pageNumber: Int = 1,


    @ColumnInfo(name = "genre_owner_id")
    var genreOwnerId: String = "",


    @ColumnInfo(name = "cached")
    var cached: Boolean = false,

    @ColumnInfo(name = "expiry_date")
    var expiryDate: Long = 0L,

    @ColumnInfo(name = "list")
    var list: String = ""
) {

    /***
     * items setter converter
     */
    fun setItems(_list: List<Long>): GenrePage {
        list = _list.joinToString(",")
        return this
    }

    /***
     * items getter converter
     */
    fun getItems(): List<Long> {
        return list.split(",").filter { it.isNotBlank() && it.isNotEmpty() }.map { it.toLong() }
    }

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