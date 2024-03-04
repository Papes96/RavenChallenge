package com.raven.home.data.remote.service

import com.raven.home.domain.model.network.ArticleResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET("svc/mostpopular/v2/{popularType}/{period}.json")
    suspend fun getNews(
        @Path("popularType") popularType: String,
        @Path("period") period: String
    ): ArticleResponseDTO
}