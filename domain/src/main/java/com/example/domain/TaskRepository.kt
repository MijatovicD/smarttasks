package com.example.domain

import com.example.domain.model.Task

interface TaskRepository {

    suspend fun getTasks(): List<Task>
}