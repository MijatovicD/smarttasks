package com.example.data.model

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("tasks") val tasks: List<TaskResponseBody>
) {
    data class TaskResponseBody(
        @SerializedName("id") val id: String,
        @SerializedName("TargetDate") val targetDate: String,
        @SerializedName("DueDate") val dueDate: String?,
        @SerializedName("Title") val title: String,
        @SerializedName("Description") val description: String,
        @SerializedName("Priority") val priority: Int?
    )
}