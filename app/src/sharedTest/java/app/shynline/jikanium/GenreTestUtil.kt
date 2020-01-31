package app.shynline.jikanium

import app.shynline.jikanium.data.requests.bygenre.db.Genre
import app.shynline.jikanium.data.requests.bygenre.db.GenrePage

object GenreTestUtil {
    fun createAnimeGenre(genre: Int, count: Long = 4000): Genre {
        return Genre(
            type = 1,
            count = count,
            genre = genre
        )
    }

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