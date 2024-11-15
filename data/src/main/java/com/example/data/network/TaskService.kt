package com.example.data.network

import com.example.data.model.TaskResponse
import retrofit2.Response
import retrofit2.http.GET

interface TaskService {

    @GET(BASE_URL)
    suspend fun getTasks(): Response<TaskResponse>

    companion object {
        const val BASE_URL: String = "http://demo2107582.mockable.io/"
    }
}