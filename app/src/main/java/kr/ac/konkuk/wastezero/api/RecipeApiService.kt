package kr.ac.konkuk.wastezero.api

import kr.ac.konkuk.wastezero.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("COOKRCP01/json/1/100")
    suspend fun getRecipes(@Query("ingredient") ingredient: String): RecipeResponse
}