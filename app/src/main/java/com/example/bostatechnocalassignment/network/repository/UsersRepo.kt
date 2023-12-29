package com.example.bostatechnocalassignment.network.repository

import com.example.bostatechnocalassignment.model.users.UsersResponse
import retrofit2.Response

interface UsersRepo {
    suspend fun getAllUsers(
    ): Response<UsersResponse>
}