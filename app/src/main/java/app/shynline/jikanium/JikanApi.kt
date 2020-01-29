package app.shynline.jikanium

import app.shynline.jikanium.data.anime.Anime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {
    @GET("anime/{id}/")
    suspend fun getAnime(@Path("id") id: Long): Response<Anime>
}