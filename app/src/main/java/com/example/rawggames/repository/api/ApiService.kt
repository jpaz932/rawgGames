package com.example.rawggames.repository.api


import com.example.rawggames.repository.model.GameDetail
import com.example.rawggames.repository.model.Games
import com.example.rawggames.repository.model.PlatForms


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getGamesBySearch(@Url url:String):Response<Games>

    @GET
    suspend fun getGameDetail(@Url url:String):Response<GameDetail>
    @GET
    suspend fun getPlatFormsBySearch(@Url url: String): Response<PlatForms>

}