package com.example.data.network

import com.example.data.model.TaskResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface TaskService {

    @GET(BASE_URL)
    suspend fun getTasks(): Response<List<TaskResponseBody>>

    companion object {
        const val BASE_URL: String = "https://demo2107582.mockable.io/"
    }
}