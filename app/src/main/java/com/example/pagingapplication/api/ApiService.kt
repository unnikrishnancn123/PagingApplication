package com.example.pagingapplication.api

import com.example.pagingapplication.model.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("todos")
    suspend fun getItems(@Query("page") page: Int, @Query("pageSize") pageSize: Int): List<Model>

}
