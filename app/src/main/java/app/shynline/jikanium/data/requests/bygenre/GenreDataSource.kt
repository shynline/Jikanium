package app.shynline.jikanium.data.requests.bygenre

import app.shynline.jikanium.data.Result
import app.shynline.jikanium.data.requests.bygenre.db.AnimePart
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


    /***
     * Local data source method specific
     * and will insert a collection of anime part into database
     */
    suspend fun insertCollectionOfAnimePart(anime: List<AnimePart>)


    /***
     * Remote data source method specific
     * and will return an anime part by id
     */
    suspend fun getAnimePartCollection(id: List<Long>): Result<List<AnimePart>>
}