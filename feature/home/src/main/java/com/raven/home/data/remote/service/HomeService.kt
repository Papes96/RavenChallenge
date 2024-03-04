package com.raven.home.data.remote.service

import retrofit2.http.GET

interface HomeService {

    //TODO("Correctly apply the Path and its answers. The API Key is provided in your PDF document")

    @GET("svc/mostpopular/v2/emailed/7.json?")
    suspend fun getNews(): List<Unit>
}
