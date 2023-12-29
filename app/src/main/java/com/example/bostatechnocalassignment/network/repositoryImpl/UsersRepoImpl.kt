package com.example.bostatechnocalassignment.network.repositoryImpl

import com.example.bostatechnocalassignment.model.users.UsersResponse
import com.example.bostatechnocalassignment.network.Apis
import com.example.bostatechnocalassignment.network.repository.UsersRepo
import retrofit2.Response
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(private val apis: Apis): UsersRepo {
    override suspend fun getAllUsers(): Response<UsersResponse> {
        return apis.getAllUsers()
    }
}