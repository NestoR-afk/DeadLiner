package com.example.deadliner.network

import com.example.deadliner.model.Deadline
import com.example.deadliner.model.ResponseDeadline
import com.example.deadliner.model.ResponseSubject
import com.example.deadliner.model.Subject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceholderApi {
    companion object {
        const val BASE_URL = "http://dedoinside.pythonanywhere.com/"
    }
    @GET("get-deadlines")
    suspend fun getDeadlines() : Response<List<ResponseDeadline>>

    @GET("get-schedule")
    suspend fun getSubjects() : Response<List<ResponseSubject>>

    @POST("create-deadlines")
    suspend fun createGroupDeadline(deadline: Deadline)
}
