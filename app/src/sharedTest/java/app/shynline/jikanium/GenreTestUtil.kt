package app.shynline.jikanium

import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage

/***
 * Genre Test Util
 */
object GenreTestUtil {
    /***
     * Create a Single Anime
     */
    fun createAnimeGenre(genre: Int, count: Long = 4000): Genre {
        return Genre(
            type = 1,
            count = count,
            genre = genre
        )
    }

    /***
     * Mock a Anime Pages for Specific genre
     */
    fun createPagesForGenre(
        genreId: String,
        pagesCount: Int = 10,
        itemEach: Int = 10
    ): List<GenrePage> {
        val l: MutableList<GenrePage> = mutableListOf()
        for (page in 0 until pagesCount) {
            val p = GenrePage(
                pageNumber = page + 1,
                genreOwnerId = genreId
            )
            val s: MutableList<Long> = mutableListOf()
            for (n in 1 until itemEach + 1) {
                s.add((page * itemEach + n).toLong())
            }

            l.add(p.setItems(s))
        }
        return l
    }
}