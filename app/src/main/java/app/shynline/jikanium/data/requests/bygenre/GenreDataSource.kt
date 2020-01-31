package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.db.GenreWithPage

/***
 * Genre data source interface
 */
interface GenreDataSource {
    /***
     * Get a specific genre with its pages if exists
     * if not return Error result
     * local specific method
     */
    suspend fun getAnimeGenre(genre: Int): Result<GenreWithPage>

    /***
     * Fetch the list of anime in a specific genre by page
     * remote specific method
     */
    suspend fun getAnimeGenreByPage(genre: Int, page: Int): Result<AnimePageWrapper>

    /***
     * insert an anime genre
     * local specific method
     */
    suspend fun insertAnimeGenre(genreWithPage: GenreWithPage)

    /***
     * update an anime genre
     * local specific method
     */
    suspend fun updateAnimeGenre(genreWithPage: GenreWithPage)
}