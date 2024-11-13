package com.example.domain.usecase

interface UseCase <in UseCaseParameter, out UseCaseResult> {
    suspend fun execute(parameter: UseCaseParameter): UseCaseResult
}